package com.workshop.wwc.rate;

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
public class RateRepository {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper: tells JDBC how to turn a database row into a Rate object
    private final RowMapper<Rate> rowMapper = (rs, rowNum) -> {
        Rate r = new Rate();
        r.setId(rs.getLong("id"));
        r.setSourceCurrency(rs.getString("source_currency"));
        r.setTargetCurrency(rs.getString("target_currency"));
        r.setRate(rs.getBigDecimal("rate"));
        r.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        r.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
        return r;
    };

    public List<Rate> findAll() {
        return jdbcTemplate.query("SELECT * FROM rate", rowMapper);
    }

    public Optional<Rate> findById(Long id) {
        List<Rate> results = jdbcTemplate.query(
                "SELECT * FROM rate WHERE id = ?", rowMapper, id
        );
        return results.stream().findFirst();
    }

    public Optional<Rate> findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency) {
        List<Rate> results = jdbcTemplate.query(
                "SELECT * FROM rate WHERE source_currency = ? AND target_currency = ?",
                rowMapper, sourceCurrency, targetCurrency
        );
        return results.stream().findFirst();
    }

    public Rate save(Rate rate) {
        Instant now = Instant.now();

        if (rate.getId() == null) {
            // INSERT
            rate.setCreatedAt(now);
            rate.setUpdatedAt(now);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO rate (source_currency, target_currency, rate, created_at, updated_at) "
                                + "VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, rate.getSourceCurrency());
                ps.setString(2, rate.getTargetCurrency());
                ps.setBigDecimal(3, rate.getRate());
                ps.setTimestamp(4, Timestamp.from(rate.getCreatedAt()));
                ps.setTimestamp(5, Timestamp.from(rate.getUpdatedAt()));
                return ps;
            }, keyHolder);

            rate.setId(keyHolder.getKey().longValue());
        } else {
            // UPDATE
            rate.setUpdatedAt(now);

            jdbcTemplate.update(
                    "UPDATE rate SET source_currency = ?, target_currency = ?, rate = ?, updated_at = ? WHERE id = ?",
                    rate.getSourceCurrency(), rate.getTargetCurrency(),
                    rate.getRate(), Timestamp.from(rate.getUpdatedAt()),
                    rate.getId()
            );
        }
        return rate;
    }
}
