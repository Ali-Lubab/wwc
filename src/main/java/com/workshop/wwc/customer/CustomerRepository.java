package com.workshop.wwc.customer;

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
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    // RowMapper: tells JDBC how to turn a database row into a Customer object
    private final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        Customer c = new Customer();
        c.setId(rs.getLong("id"));
        c.setFirstName(rs.getString("first_name"));
        c.setLastName(rs.getString("last_name"));
        c.setDob(rs.getTimestamp("dob").toInstant());
        c.setAddress(rs.getString("address"));
        c.setEmailAddress(rs.getString("email_address"));
        c.setPassword(rs.getString("password"));
        c.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        c.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
        return c;
    };

    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", rowMapper);
    }

    public Optional<Customer> findById(Long id) {
        List<Customer> results = jdbcTemplate.query(
                "SELECT * FROM customer WHERE id = ?", rowMapper, id
        );
        return results.stream().findFirst();
    }

    public Optional<Customer> findByEmailAddress(String emailAddress) {
        List<Customer> results = jdbcTemplate.query(
                "SELECT * FROM customer WHERE email_address = ?",
                rowMapper, emailAddress
        );
        return results.stream().findFirst();
    }

    public Optional<Customer> findByEmailAddressAndPassword(String emailAddress, String password) {
        List<Customer> results = jdbcTemplate.query(
                "SELECT * FROM customer WHERE email_address = ? AND password = ?",
                rowMapper, emailAddress, password
        );
        return results.stream().findFirst();
    }

    public Customer save(Customer customer) {
        Instant now = Instant.now();

        if (customer.getId() == null) {
            // INSERT new customer
            customer.setCreatedAt(now);
            customer.setUpdatedAt(now);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO customer (first_name, last_name, dob, address, email_address, password, created_at, updated_at) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setTimestamp(3, Timestamp.from(customer.getDob()));
                ps.setString(4, customer.getAddress());
                ps.setString(5, customer.getEmailAddress());
                ps.setString(6, customer.getPassword());
                ps.setTimestamp(7, Timestamp.from(customer.getCreatedAt()));
                ps.setTimestamp(8, Timestamp.from(customer.getUpdatedAt()));
                return ps;
            }, keyHolder);

            customer.setId(keyHolder.getKey().longValue());
        } else {
            // UPDATE existing customer
            customer.setUpdatedAt(now);

            jdbcTemplate.update(
                    "UPDATE customer SET first_name = ?, last_name = ?, dob = ?, address = ?, "
                            + "email_address = ?, password = ?, updated_at = ? WHERE id = ?",
                    customer.getFirstName(), customer.getLastName(),
                    Timestamp.from(customer.getDob()), customer.getAddress(),
                    customer.getEmailAddress(), customer.getPassword(),
                    Timestamp.from(customer.getUpdatedAt()), customer.getId()
            );
        }
        return customer;
    }
}
