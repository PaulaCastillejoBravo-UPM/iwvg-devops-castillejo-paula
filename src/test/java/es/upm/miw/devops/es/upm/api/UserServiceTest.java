package es.upm.miw.devops.es.upm.api;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import es.upm.miw.devops.es.upm.api.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import es.upm.miw.devops.es.upm.api.services.exceptions.UserNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

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
}
