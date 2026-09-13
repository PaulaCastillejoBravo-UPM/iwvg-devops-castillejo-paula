package es.upm.miw.devops.es.upm.api;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import es.upm.miw.devops.es.upm.api.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import es.upm.miw.devops.es.upm.api.services.exceptions.UserNotFoundException;
import es.upm.miw.devops.seeder.UserSeeder;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSeeder userSeeder;

    @BeforeEach
    void setUp() {
        userSeeder.seed();
    }

    @Test
    void testFindById() {
        UUID userId = UUID.fromString(
                "11111111-1111-1111-1111-111111111111"
        );

        User user = userService.findById(userId);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getFirstName()).isEqualTo("Alice");
        assertThat(user.getFamilyName()).isEqualTo("Smith");
        assertThat(user.getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void testFindByIdNotFound() {
        UUID userId = UUID.fromString(
                "99999999-9999-9999-9999-999999999999"
        );

        assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testFindByBillableTrue() {
        List<User> users = userService.findByBillable(true);

        assertThat(users).hasSize(2);
        assertThat(users)
                .extracting(User::getFirstName)
                .containsExactlyInAnyOrder("Alice", "Charlie");
    }

    @Test
    void testFindByBillableFalse() {
        List<User> users = userService.findByBillable(false);

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getFirstName()).isEqualTo("Bob");
    }

    @Test
    void testFindByBillableWithoutFilter() {
        List<User> users = userService.findByBillable(null);

        assertThat(users).hasSize(3);
    }

    @Test
    void testDeleteById() {
        UUID userId = UUID.fromString(
                "33333333-3333-3333-3333-333333333333"
        );

        userService.deleteById(userId);

        assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testDeleteByIdNotFound() {
        UUID userId = UUID.fromString(
                "99999999-9999-9999-9999-999999999999"
        );

        assertThatThrownBy(() -> userService.deleteById(userId))
                .isInstanceOf(UserNotFoundException.class);
    }
}
