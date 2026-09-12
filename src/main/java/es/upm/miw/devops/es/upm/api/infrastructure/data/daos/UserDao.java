package es.upm.miw.devops.es.upm.api.infrastructure.data.daos;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(UUID id) {
        String sql = """
                SELECT
                    id,
                    first_name,
                    family_name,
                    email,
                    mobile,
                    address,
                    city,
                    postal_code,
                    password
                FROM users
                WHERE id = ?
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new User(
                        resultSet.getObject("id", UUID.class),
                        resultSet.getString("first_name"),
                        resultSet.getString("family_name"),
                        resultSet.getString("email"),
                        resultSet.getString("mobile"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getInt("postal_code"),
                        resultSet.getString("password")
                ),
                id
        ).stream().findFirst().orElse(null);
    }
}