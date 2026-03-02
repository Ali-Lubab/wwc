package com.workshop.wwc.recipient;

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
public class RecipientRepository {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper: tells JDBC how to turn a database row into a Recipient object
    private final RowMapper<Recipient> rowMapper = (rs, rowNum) -> {
        Recipient r = new Recipient();
        r.setId(rs.getLong("id"));
        r.setFirstName(rs.getString("first_name"));
        r.setLastName(rs.getString("last_name"));
        r.setAccountNumber(rs.getString("account_number"));
        r.setActive(rs.getBoolean("is_active"));
        return r;
    };

    public List<Recipient> findAll() {
        return jdbcTemplate.query("SELECT * FROM recipient", rowMapper);
    }

    public Recipient findById(Long id) {
        List<Recipient> results = jdbcTemplate.query(
                "SELECT * FROM recipient WHERE id = ?", rowMapper, id
        );
        return results.isEmpty() ? null : results.get(0);
    }

    public Recipient save(Recipient recipient) {
        if (recipient.getId() == null) {
            // INSERT
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO recipient (first_name, last_name, account_number, is_active) "
                                + "VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, recipient.getFirstName());
                ps.setString(2, recipient.getLastName());
                ps.setString(3, recipient.getAccountNumber());
                ps.setBoolean(4, recipient.isActive());
                return ps;
            }, keyHolder);

            recipient.setId(keyHolder.getKey().longValue());
        } else {
            // UPDATE
            jdbcTemplate.update(
                    "UPDATE recipient SET first_name = ?, last_name = ?, "
                            + "account_number = ?, is_active = ? WHERE id = ?",
                    recipient.getFirstName(), recipient.getLastName(),
                    recipient.getAccountNumber(), recipient.isActive(),
                    recipient.getId()
            );
        }
        return recipient;
    }
}
