package com.workshop.wwc.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
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
        b.setOwnerId(rs.getLong("owner_id"));
        b.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        b.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
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

    public Optional<Balance> findByOwnerIdAndCurrency(Long ownerId, String currency) {
        List<Balance> results = jdbcTemplate.query(
                "SELECT * FROM balance WHERE owner_id = ? AND currency = ?",
                rowMapper, ownerId, currency
        );
        return results.stream().findFirst();
    }

    public Balance save(Balance balance) {
        Instant now = Instant.now();

        if (balance.getId() == null) {
            // INSERT
            balance.setCreatedAt(now);
            balance.setUpdatedAt(now);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO balance (currency, amount, owner_id, created_at, updated_at) "
                                + "VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, balance.getCurrency());
                ps.setBigDecimal(2, balance.getAmount());
                ps.setLong(3, balance.getOwnerId());
                ps.setTimestamp(4, Timestamp.from(balance.getCreatedAt()));
                ps.setTimestamp(5, Timestamp.from(balance.getUpdatedAt()));
                return ps;
            }, keyHolder);

            balance.setId(keyHolder.getKey().longValue());
        } else {
            // UPDATE
            balance.setUpdatedAt(now);

            jdbcTemplate.update(
                    "UPDATE balance SET currency = ?, amount = ?, owner_id = ?, updated_at = ? WHERE id = ?",
                    balance.getCurrency(), balance.getAmount(),
                    balance.getOwnerId(), Timestamp.from(balance.getUpdatedAt()),
                    balance.getId()
            );
        }
        return balance;
    }
}
