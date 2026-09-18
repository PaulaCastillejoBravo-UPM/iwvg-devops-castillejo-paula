package es.upm.miw.devops.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Profile({"dev", "pre", "prop", "test"})
@Component
public class UserSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public UserSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        seed();
    }

    public void seed() {
        jdbcTemplate.update("DELETE FROM users");

        jdbcTemplate.update("""
            INSERT INTO users (
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
            ) VALUES (
                '11111111-1111-1111-1111-111111111111',
                'Alice',
                'Smith',
                'alice@example.com',
                '600000001',
                '12345678A',
                'Calle Mayor 1',
                'Madrid',
                28001,
                'Madrid',
                'password',
                false,
                'CUSTOMER'
            )
            """);

        jdbcTemplate.update("""
            INSERT INTO users (
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
            ) VALUES (
                '22222222-2222-2222-2222-222222222222',
                'Bob',
                'Smith',
                'bob@example.com',
                '600000002',
                '87654321B',
                'Calle Mayor 2',
                'Madrid',
                28002,
                NULL,
                'password',
                false,
                'OPERATOR'
            )
            """);

        jdbcTemplate.update("""
            INSERT INTO users (
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
            ) VALUES (
                '33333333-3333-3333-3333-333333333333',
                'Charlie',
                'Smith',
                'charlie@example.com',
                '600000003',
                '11223344C',
                'Calle Mayor 3',
                'Madrid',
                28003,
                'Madrid',
                'password',
                false,
                'ADMIN'
            )
            """);
    }
}