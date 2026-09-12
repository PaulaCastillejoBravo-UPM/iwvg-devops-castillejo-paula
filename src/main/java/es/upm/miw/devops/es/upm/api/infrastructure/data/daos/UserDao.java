package es.upm.miw.devops.es.upm.api.infrastructure.data.daos;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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
                    identity,
                    address,
                    city,
                    postal_code,
                    province,
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
                        resultSet.getString("identity"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getObject("postal_code", Integer.class),
                        resultSet.getString("province"),
                        resultSet.getString("password")
                ),
                id
        ).stream().findFirst().orElse(null);
    }

    public List<User> findByBillable(Boolean billable) {
        String sql = """
            SELECT
                id,
                first_name,
                family_name,
                email,
                mobile,
                identity,
                address,
                city,
                postal_code,
                province,
                password
            FROM users
            """;

        if (billable != null) {
            sql += """
                WHERE (
                    COALESCE(TRIM(first_name), '') <> ''
                    AND COALESCE(TRIM(family_name), '') <> ''
                    AND COALESCE(TRIM(email), '') <> ''
                    AND COALESCE(TRIM(identity), '') <> ''
                    AND COALESCE(TRIM(address), '') <> ''
                    AND COALESCE(TRIM(city), '') <> ''
                    AND COALESCE(TRIM(province), '') <> ''
                    AND postal_code IS NOT NULL
                ) = ?
                """;
        }

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new User(
                        resultSet.getObject("id", UUID.class),
                        resultSet.getString("first_name"),
                        resultSet.getString("family_name"),
                        resultSet.getString("email"),
                        resultSet.getString("mobile"),
                        resultSet.getString("identity"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getObject("postal_code", Integer.class),
                        resultSet.getString("province"),
                        resultSet.getString("password")
                ),
                billable
        );
    }
}