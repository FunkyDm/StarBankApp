package pro.sky.StarBankApp.StarBankApp.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UUID getUserIdByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT id FROM users WHERE username = ?",
                    UUID.class,
                    username
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String getFullNameByUsername(String username) {
        String sql = "SELECT FIRST_NAME, LAST_NAME FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                return firstName + " " + lastName;
            }, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
