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
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BalanceRepository {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper: tells JDBC how to turn a database row into a Balance object
    private final RowMapper<Balance> rowMapper = (rs, rowNum) -> {
        Balance b = new Balance();
        b.setId(rs.getLong("id"));
        b.setCurrency(rs.getString("currency"));
        b.setAmount(rs.getBigDecimal("amount"));
        return b;
    };

    public List<Balance> findAll() {
        return jdbcTemplate.query("SELECT * FROM balance", rowMapper);
    }

    public Optional<Balance> findById(Long id) {
        List<Balance> results = jdbcTemplate.query(
                "SELECT * FROM balance WHERE id = ?", rowMapper, id
        );
        return results.stream().findFirst();
    }

    public Optional<Balance> findByCurrency(String currency) {
        List<Balance> results = jdbcTemplate.query(
                "SELECT * FROM balance WHERE currency = ?",
                rowMapper, currency
        );
        return results.stream().findFirst();
    }

    public Balance save(Balance balance) {
        if (balance.getId() == null) {
            // INSERT
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO balance (currency, amount) "
                                + "VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, balance.getCurrency());
                ps.setBigDecimal(2, balance.getAmount());
                return ps;
            }, keyHolder);

            balance.setId(keyHolder.getKey().longValue());
        } else {
            // UPDATE
            jdbcTemplate.update(
                    "UPDATE balance SET currency = ?, amount = ? WHERE id = ?",
                    balance.getCurrency(), balance.getAmount(),
                    balance.getId()
            );
        }
        return balance;
    }
}
