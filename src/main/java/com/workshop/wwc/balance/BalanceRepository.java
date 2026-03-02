package com.workshop.wwc.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BalanceRepository {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper: tells JDBC how to turn a database row into a Balance object
    private final RowMapper<Balance> rowMapper = (rs, rowNum) -> {
        Balance b = new Balance();
        b.setId(rs.getLong("id"));
        b.setRecipientId(rs.getLong("recipient_id"));
        b.setCurrency(rs.getString("currency"));
        b.setAmount(rs.getBigDecimal("amount"));
        return b;
    };

    public List<Balance> findAll() {
        return jdbcTemplate.query("SELECT * FROM balance", rowMapper);
    }

    public Balance findById(Long id) {
        List<Balance> results = jdbcTemplate.query(
                "SELECT * FROM balance WHERE id = ?", rowMapper, id
        );
        return results.isEmpty() ? null : results.get(0);
    }

    public Balance findByCurrency(String currency) {
        List<Balance> results = jdbcTemplate.query(
                "SELECT * FROM balance WHERE currency = ?",
                rowMapper, currency
        );
        return results.isEmpty() ? null : results.get(0);
    }

    public Balance findByRecipientIdAndCurrency(Long recipientId, String currency) {
        List<Balance> results = jdbcTemplate.query(
                "SELECT * FROM balance WHERE recipient_id = ? AND currency = ?",
                rowMapper, recipientId, currency
        );
        return results.isEmpty() ? null : results.get(0);
    }

    public Balance save(Balance balance) {
        if (balance.getId() == null) {
            // INSERT
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO balance (recipient_id, currency, amount) "
                                + "VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, balance.getRecipientId());
                ps.setString(2, balance.getCurrency());
                ps.setBigDecimal(3, balance.getAmount());
                return ps;
            }, keyHolder);

            balance.setId(keyHolder.getKey().longValue());
        } else {
            // UPDATE
            jdbcTemplate.update(
                    "UPDATE balance SET recipient_id = ?, currency = ?, amount = ? WHERE id = ?",
                    balance.getRecipientId(), balance.getCurrency(), balance.getAmount(),
                    balance.getId()
            );
        }
        return balance;
    }
}
