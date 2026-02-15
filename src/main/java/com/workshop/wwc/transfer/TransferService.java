package com.workshop.wwc.transfer;

import com.workshop.wwc.balance.Balance;
import com.workshop.wwc.balance.BalanceRepository;
import com.workshop.wwc.rate.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final RateRepository rateRepository;
    private final BalanceRepository balanceRepository;
    private final TransferRepository transferRepository;

    public Transfer create(Transfer transfer) {
        var exchangeRate =
                rateRepository.findBySourceCurrencyAndTargetCurrency(transfer.getSourceCurrency(), transfer.getTargetCurrency());
        var targetAmount = transfer.getSourceAmount().multiply(exchangeRate.get().getRate());
        transfer.setTargetAmount(targetAmount);
        Optional<Balance> sourceBalance = balanceRepository.findByCurrency(transfer.getSourceCurrency());
        Optional<Balance> targetBalance = balanceRepository.findByCurrency(transfer.getTargetCurrency());
        if (sourceBalance.isEmpty() || targetBalance.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance not found for one of the currencies");
        }
        if (sourceBalance.get().getAmount().compareTo(transfer.getSourceAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds in source balance");
        }
        sourceBalance.get().setAmount(sourceBalance.get().getAmount().add(transfer.getSourceAmount()));
        targetBalance.get().setAmount(targetBalance.get().getAmount().subtract(transfer.getTargetAmount()));
        balanceRepository.save(sourceBalance.get());
        balanceRepository.save(targetBalance.get());
        return transferRepository.save(transfer);
    }
}
