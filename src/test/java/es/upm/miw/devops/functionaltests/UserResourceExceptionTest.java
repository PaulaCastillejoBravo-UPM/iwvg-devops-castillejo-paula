package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.es.upm.api.resources.dtos.UserUpdateDto;
import es.upm.miw.devops.es.upm.api.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserResourceExceptionTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private UserService userService;

    @Test
    void testInternalServerError() {
        UUID userId = UUID.fromString(
                "11111111-1111-1111-1111-111111111111"
        );

        when(userService.findById(userId))
                .thenThrow(new RuntimeException("Database error"));

        webTestClient.get()
                .uri("/user/11111111-1111-1111-1111-111111111111")
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody()
                .jsonPath("$.code")
                .isEqualTo(500)
                .jsonPath("$.error")
                .isEqualTo("RuntimeException")
                .jsonPath("$.message")
                .isEqualTo("ERROR");
    }

    @Test
    void testFindByBillableInternalServerError() {
        when(userService.findByBillable(true))
                .thenThrow(new RuntimeException("Database error"));

        webTestClient.get()
                .uri("/user?billable=true")
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody()
                .jsonPath("$.code")
                .isEqualTo(500)
                .jsonPath("$.error")
                .isEqualTo("RuntimeException")
                .jsonPath("$.message")
                .isEqualTo("ERROR");
    }

    @Test
    void testUpdateActiveInternalServerError() {
        UUID userId = UUID.fromString(
                "11111111-1111-1111-1111-111111111111"
        );

        when(userService.updateActive(userId, true))
                .thenThrow(new RuntimeException("Database error"));

        webTestClient.put()
                .uri("/user/11111111-1111-1111-1111-111111111111/active")
                .bodyValue(true)
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody()
                .jsonPath("$.code")
                .isEqualTo(500)
                .jsonPath("$.error")
                .isEqualTo("RuntimeException")
                .jsonPath("$.message")
                .isEqualTo("ERROR");
    }

    @Test
    void testUpdateByIdInternalServerError() {
        UUID userId = UUID.fromString(
                "11111111-1111-1111-1111-111111111111"
        );

        when(userService.updateById(eq(userId), any(UserUpdateDto.class)))
                .thenThrow(new RuntimeException("Database error"));

        webTestClient.put()
                .uri("/user/11111111-1111-1111-1111-111111111111")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "firstName": "Alice",
                        "familyName": "Smith",
                        "email": "alice@example.com",
                        "mobile": "600000000",
                        "identity": "12345678A",
                        "address": "Calle Test 1",
                        "city": "Madrid",
                        "postalCode": 28001,
                        "province": "Madrid",
                        "password": "password",
                        "active": true,
                        "role": "ADMNI"
                    }
                    """)
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody()
                .jsonPath("$.code")
                .isEqualTo(500)
                .jsonPath("$.error")
                .isEqualTo("RuntimeException")
                .jsonPath("$.message")
                .isEqualTo("ERROR");
    }

    @Test
    void testUpdateActiveListInternalServerError() {
        when(userService.updateActive(anyList()))
                .thenThrow(new RuntimeException("ERROR"));

        webTestClient.patch()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                [
                    {
                        "id": "11111111-1111-1111-1111-111111111111",
                        "active": true
                    }
                ]
                """)
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody()
                .jsonPath("$.code")
                .isEqualTo(500)
                .jsonPath("$.error")
                .isEqualTo("RuntimeException")
                .jsonPath("$.message")
                .isEqualTo("ERROR");
    }
}