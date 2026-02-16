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
        updateTargetAmount(transfer);
        var currentBalances = getCurrentBalances(transfer);
        updateCurrentBalances(transfer, currentBalances);
        return transferRepository.save(transfer);
    }

    private void updateTargetAmount(Transfer transfer) {
        var exchangeRate =
                rateRepository.findBySourceCurrencyAndTargetCurrency(transfer.getSourceCurrency(), transfer.getTargetCurrency());
        var targetAmount = transfer.getSourceAmount().multiply(exchangeRate.get().getRate());
        transfer.setTargetAmount(targetAmount);
    }

    private Result getCurrentBalances(Transfer transfer) {
        Optional<Balance> sourceBalance = balanceRepository.findByCurrency(transfer.getSourceCurrency());
        Optional<Balance> targetBalance = balanceRepository.findByCurrency(transfer.getTargetCurrency());
        if (sourceBalance.isEmpty() || targetBalance.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance not found for one of the currencies");
        }
        if (sourceBalance.get().getAmount().compareTo(transfer.getSourceAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds in source balance");
        }
        return new Result(sourceBalance.get(), targetBalance.get());
    }

    private void updateCurrentBalances(Transfer transfer, Result currentBalances) {
        currentBalances.sourceBalance().setAmount(currentBalances.sourceBalance().getAmount().add(transfer.getSourceAmount()));
        currentBalances.targetBalance().setAmount(currentBalances.targetBalance().getAmount().subtract(transfer.getTargetAmount()));
        balanceRepository.save(currentBalances.sourceBalance());
        balanceRepository.save(currentBalances.targetBalance());
    }

    private record Result(Balance sourceBalance, Balance targetBalance) {
    }
}
