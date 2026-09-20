package es.upm.miw.devops.es.upm.api;

import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import es.upm.miw.devops.es.upm.api.infrastructure.data.models.UserActiveUpdate;
import es.upm.miw.devops.es.upm.api.resources.dtos.UserUpdateDto;
import es.upm.miw.devops.es.upm.api.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static es.upm.miw.devops.es.upm.api.infrastructure.data.models.Role.ADMIN;
import static es.upm.miw.devops.es.upm.api.infrastructure.data.models.Role.CUSTOMER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import es.upm.miw.devops.es.upm.api.services.exceptions.UserNotFoundException;
import es.upm.miw.devops.UserSeeder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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
        assertThat(users.getFirst().getFirstName()).isEqualTo("Bob");
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

    @Test
    void testUpdateActiveTrue() {
        UUID userId = UUID.fromString(
                "11111111-1111-1111-1111-111111111111"
        );

        User user = userService.updateActive(userId, true);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getActive()).isTrue();
    }

    @Test
    void testUpdateActiveFalse() {
        UUID userId = UUID.fromString(
                "11111111-1111-1111-1111-111111111111"
        );

        User user = userService.updateActive(userId, false);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getActive()).isFalse();
    }

    @Test
    void testUpdateActiveNotFound() {
        UUID userId = UUID.fromString(
                "99999999-9999-9999-9999-999999999999"
        );

        assertThatThrownBy(() -> userService.updateActive(userId, true))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testUpdateById() {
        UUID userId = UUID.fromString("33333333-3333-3333-3333-333333333333");

        UserUpdateDto user = new UserUpdateDto(
                "Charlie Updated",
                "Brown",
                "charlie.updated@example.com",
                "600999999",
                "87654321B",
                "Calle Nueva 10",
                "Madrid",
                28010,
                "Madrid",
                "newpassword",
                true,
                ADMIN
        );

        User updatedUser = userService.updateById(userId, user);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(userId);
        assertThat(updatedUser.getFirstName()).isEqualTo("Charlie Updated");
        assertThat(updatedUser.getFamilyName()).isEqualTo("Brown");
        assertThat(updatedUser.getEmail()).isEqualTo("charlie.updated@example.com");
        assertThat(updatedUser.getMobile()).isEqualTo("600999999");
        assertThat(updatedUser.getIdentity()).isEqualTo("87654321B");
        assertThat(updatedUser.getAddress()).isEqualTo("Calle Nueva 10");
        assertThat(updatedUser.getCity()).isEqualTo("Madrid");
        assertThat(updatedUser.getPostalCode()).isEqualTo(28010);
        assertThat(updatedUser.getProvince()).isEqualTo("Madrid");
        assertThat(updatedUser.getPassword()).isEqualTo("newpassword");
        assertThat(updatedUser.getActive()).isTrue();
        assertThat(updatedUser.getRole()).isEqualTo(ADMIN);
    }

    @Test
    void testUpdateByIdNotFound() {
        UUID userId = UUID.fromString("99999999-9999-9999-9999-999999999999");

        UserUpdateDto user = new UserUpdateDto(
                "Test",
                "User",
                "test@example.com",
                "600000000",
                "12345678A",
                "Calle Test 1",
                "Madrid",
                28001,
                "Madrid",
                "password",
                true,
                CUSTOMER
        );

        assertThatThrownBy(() -> userService.updateById(userId, user))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testUpdateActiveList() {
        List<UserActiveUpdate> users = List.of(
                new UserActiveUpdate(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        true
                ),
                new UserActiveUpdate(
                        UUID.fromString("22222222-2222-2222-2222-222222222222"),
                        true
                )
        );

        List<User> updatedUsers = userService.updateActive(users);

        assertThat(updatedUsers).hasSize(2);

        assertThat(updatedUsers)
                .extracting(User::getId)
                .containsExactlyInAnyOrder(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        UUID.fromString("22222222-2222-2222-2222-222222222222")
                );

        assertThat(updatedUsers)
                .extracting(User::getActive)
                .containsOnly(true);
    }

    @Test
    void testUpdateActiveListNotFound() {
        List<UserActiveUpdate> users = List.of(
                new UserActiveUpdate(
                        UUID.fromString("99999999-9999-9999-9999-999999999999"),
                        true
                )
        );

        assertThatThrownBy(() -> userService.updateActive(users))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void testUpdateActiveListAdminCannotBeDeactivated() {
        List<UserActiveUpdate> users = List.of(
                new UserActiveUpdate(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        false
                ),
                new UserActiveUpdate(
                        UUID.fromString("22222222-2222-2222-2222-222222222222"),
                        true
                ),
                new UserActiveUpdate(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        false
                )
        );

        List<User> updatedUsers = userService.updateActive(users);

        assertThat(updatedUsers)
                .extracting(User::getActive)
                .containsExactly(false, true, true);
    }
}
