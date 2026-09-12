package es.upm.miw.devops.functionaltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import es.upm.miw.devops.es.upm.api.UserController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserResourceFT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserController userController;

    @Test
    void testFindById() {
        webTestClient.get()
                .uri("/users/11111111-1111-1111-1111-111111111111")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo("11111111-1111-1111-1111-111111111111")
                .jsonPath("$.firstName")
                .isEqualTo("Alice")
                .jsonPath("$.familyName")
                .isEqualTo("Smith")
                .jsonPath("$.email")
                .isEqualTo("alice@example.com");
    }
}
