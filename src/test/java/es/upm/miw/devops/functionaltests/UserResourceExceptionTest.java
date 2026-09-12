package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.es.upm.api.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

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
                .uri("/users/11111111-1111-1111-1111-111111111111")
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