package com.workshop.wwc.transfer;

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
public class TransferRepository {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper: tells JDBC how to turn a database row into a Transfer object
    private final RowMapper<Transfer> rowMapper = (rs, rowNum) -> {
        Transfer t = new Transfer();
        t.setId(rs.getLong("id"));
        t.setSenderId(rs.getLong("sender_id"));
        t.setRecipientId(rs.getLong("recipient_id"));
        t.setSourceAmount(rs.getBigDecimal("source_amount"));
        t.setSourceCurrency(rs.getString("source_currency"));
        t.setTargetAmount(rs.getBigDecimal("target_amount"));
        t.setTargetCurrency(rs.getString("target_currency"));
        t.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        t.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
        return t;
    };

    public List<Transfer> findAll() {
        return jdbcTemplate.query("SELECT * FROM transfer", rowMapper);
    }

    public Optional<Transfer> findById(Long id) {
        List<Transfer> results = jdbcTemplate.query(
                "SELECT * FROM transfer WHERE id = ?", rowMapper, id
        );
        return results.stream().findFirst();
    }

    public List<Transfer> findBySenderIdOrderByCreatedAtDesc(Long senderId) {
        return jdbcTemplate.query(
                "SELECT * FROM transfer WHERE sender_id = ? ORDER BY created_at DESC",
                rowMapper, senderId
        );
    }

    public Transfer save(Transfer transfer) {
        Instant now = Instant.now();

        if (transfer.getId() == null) {
            // INSERT
            transfer.setCreatedAt(now);
            transfer.setUpdatedAt(now);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO transfer (sender_id, recipient_id, source_amount, source_currency, target_amount, target_currency, created_at, updated_at) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, transfer.getSenderId());
                ps.setLong(2, transfer.getRecipientId());
                ps.setBigDecimal(3, transfer.getSourceAmount());
                ps.setString(4, transfer.getSourceCurrency());
                ps.setBigDecimal(5, transfer.getTargetAmount());
                ps.setString(6, transfer.getTargetCurrency());
                ps.setTimestamp(7, Timestamp.from(transfer.getCreatedAt()));
                ps.setTimestamp(8, Timestamp.from(transfer.getUpdatedAt()));
                return ps;
            }, keyHolder);

            transfer.setId(keyHolder.getKey().longValue());
        } else {
            // UPDATE
            transfer.setUpdatedAt(now);

            jdbcTemplate.update(
                    "UPDATE transfer SET sender_id = ?, recipient_id = ?, source_amount = ?, "
                            + "source_currency = ?, target_amount = ?, target_currency = ?, updated_at = ? WHERE id = ?",
                    transfer.getSenderId(), transfer.getRecipientId(),
                    transfer.getSourceAmount(), transfer.getSourceCurrency(),
                    transfer.getTargetAmount(), transfer.getTargetCurrency(),
                    Timestamp.from(transfer.getUpdatedAt()), transfer.getId()
            );
        }
        return transfer;
    }
}
