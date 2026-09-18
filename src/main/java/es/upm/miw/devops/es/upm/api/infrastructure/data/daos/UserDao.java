package es.upm.miw.devops.es.upm.api.infrastructure.data.daos;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.Role;
import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserDao {

    private static final String USER_COLUMNS = """
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
            password,
            active,
            role
            """;

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findById(UUID id) {
        String sql = """
                SELECT %s
                FROM users
                WHERE id = ?
                """.formatted(USER_COLUMNS);

        return jdbcTemplate.query(sql, this::mapUser, id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<User> findByBillable(Boolean billable) {
        String sql = """
                SELECT %s
                FROM users
                """.formatted(USER_COLUMNS);

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

            return jdbcTemplate.query(sql, this::mapUser, billable);
        }

        return jdbcTemplate.query(sql, this::mapUser);
    }

    public void deleteById(UUID id) {
        String sql = """
                DELETE FROM users
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    public void updateActive(UUID id, Boolean active) {
        String sql = """
                UPDATE users
                SET active = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, active, id);
    }

    public void updateById(UUID id, User user) {
        String sql = """
                UPDATE users
                SET first_name = ?,
                    family_name = ?,
                    email = ?,
                    mobile = ?,
                    identity = ?,
                    address = ?,
                    city = ?,
                    postal_code = ?,
                    province = ?,
                    password = ?,
                    active = ?,
                    role = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                user.getFirstName(),
                user.getFamilyName(),
                user.getEmail(),
                user.getMobile(),
                user.getIdentity(),
                user.getAddress(),
                user.getCity(),
                user.getPostalCode(),
                user.getProvince(),
                user.getPassword(),
                user.getActive(),
                user.getRole().name(),
                id
        );
    }

    private User mapUser(java.sql.ResultSet resultSet, int rowNum) throws java.sql.SQLException {
        return new User(
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
                resultSet.getString("password"),
                resultSet.getBoolean("active"),
                Role.valueOf(resultSet.getString("role"))
        );
    }
}