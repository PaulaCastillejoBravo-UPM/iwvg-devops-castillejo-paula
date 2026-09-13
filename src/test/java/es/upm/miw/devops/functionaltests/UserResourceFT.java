package es.upm.miw.devops.functionaltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import es.upm.miw.devops.seeder.UserSeeder;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserResourceFT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserSeeder userSeeder;

    @BeforeEach
    void setUp() {
        userSeeder.seed();
    }

    @Test
    void testFindById() {
        webTestClient.get()
                .uri("/user/11111111-1111-1111-1111-111111111111")
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

    @Test
    void testFindByIdNotFound() {
        webTestClient.get()
                .uri("/user/99999999-9999-9999-9999-999999999999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindByIdBadRequest() {
        webTestClient.get()
                .uri("/user/not-a-valid-uuid")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testFindByBillableTrue() {
        webTestClient.get()
                .uri("/user?billable=true")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()")
                .isEqualTo(2);
    }

    @Test
    void testFindByBillableFalse() {
        webTestClient.get()
                .uri("/user?billable=false")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()")
                .isEqualTo(1)
                .jsonPath("$[0].firstName")
                .isEqualTo("Bob");
    }

    @Test
    void testFindUsersWithoutBillableFilter() {
        webTestClient.get()
                .uri("/user")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()")
                .isEqualTo(3);
    }

    @Test
    void testFindByBillableBadRequest() {
        webTestClient.get()
                .uri("/user?billable=not-a-boolean")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testDeleteById() {
        webTestClient.delete()
                .uri("/user/33333333-3333-3333-3333-333333333333")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteByIdNotFound() {
        webTestClient.delete()
                .uri("/user/99999999-9999-9999-9999-999999999999")
                .exchange()
                .expectStatus().isNotFound();
    }
}
