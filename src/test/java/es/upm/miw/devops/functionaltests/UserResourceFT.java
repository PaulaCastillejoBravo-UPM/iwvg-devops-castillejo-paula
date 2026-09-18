package es.upm.miw.devops.functionaltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import es.upm.miw.devops.UserSeeder;
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

    @Test
    void testUpdateActiveTrue() {
        webTestClient.put()
                .uri("/user/11111111-1111-1111-1111-111111111111/active")
                .bodyValue(true)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo("11111111-1111-1111-1111-111111111111")
                .jsonPath("$.active")
                .isEqualTo(true);
    }

    @Test
    void testUpdateActiveFalse() {
        webTestClient.put()
                .uri("/user/11111111-1111-1111-1111-111111111111/active")
                .bodyValue(false)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo("11111111-1111-1111-1111-111111111111")
                .jsonPath("$.active")
                .isEqualTo(false);
    }

    @Test
    void testUpdateActiveNotFound() {
        webTestClient.put()
                .uri("/user/99999999-9999-9999-9999-999999999999/active")
                .bodyValue(true)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateActiveBadRequest() {
        webTestClient.put()
                .uri("/user/not-a-valid-uuid/active")
                .bodyValue(true)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateById() {
        webTestClient.put()
                .uri("/user/33333333-3333-3333-3333-333333333333")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "firstName": "Charlie Updated",
                        "familyName": "Brown",
                        "email": "charlie.updated@example.com",
                        "mobile": "600999999",
                        "identity": "87654321B",
                        "address": "Calle Nueva 10",
                        "city": "Madrid",
                        "postalCode": 28010,
                        "province": "Madrid",
                        "password": "newpassword",
                        "active": true,
                        "role": "ADMIN"
                    }
                    """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo("33333333-3333-3333-3333-333333333333")
                .jsonPath("$.firstName")
                .isEqualTo("Charlie Updated")
                .jsonPath("$.familyName")
                .isEqualTo("Brown")
                .jsonPath("$.email")
                .isEqualTo("charlie.updated@example.com")
                .jsonPath("$.mobile")
                .isEqualTo("600999999")
                .jsonPath("$.identity")
                .isEqualTo("87654321B")
                .jsonPath("$.address")
                .isEqualTo("Calle Nueva 10")
                .jsonPath("$.city")
                .isEqualTo("Madrid")
                .jsonPath("$.postalCode")
                .isEqualTo(28010)
                .jsonPath("$.province")
                .isEqualTo("Madrid")
                .jsonPath("$.password")
                .isEqualTo("newpassword")
                .jsonPath("$.active")
                .isEqualTo(true)
                .jsonPath("$.role")
                .isEqualTo("ADMIN");
    }

    @Test
    void testUpdateByIdNotFound() {
        webTestClient.put()
                .uri("/user/99999999-9999-9999-9999-999999999999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                    {
                        "firstName": "Test",
                        "familyName": "User",
                        "email": "test@example.com",
                        "mobile": "600000000",
                        "identity": "12345678A",
                        "address": "Calle Test 1",
                        "city": "Madrid",
                        "postalCode": 28001,
                        "province": "Madrid",
                        "password": "password",
                        "active": true
                    }
                    """)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateByIdBadRequest() {
        webTestClient.put()
                .uri("/user/not-a-valid-uuid")
                .bodyValue("""
                    {
                        "firstName": "Test",
                        "familyName": "User",
                        "email": "test@example.com",
                        "mobile": "600000000",
                        "identity": "12345678A",
                        "address": "Calle Test 1",
                        "city": "Madrid",
                        "postalCode": 28001,
                        "province": "Madrid",
                        "password": "password",
                        "active": true
                    }
                    """)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdateActiveList() {
        webTestClient.patch()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                [
                    {
                        "id": "11111111-1111-1111-1111-111111111111",
                        "active": true
                    },
                    {
                        "id": "22222222-2222-2222-2222-222222222222",
                        "active": true
                    }
                ]
                """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()")
                .isEqualTo(2)
                .jsonPath("$[0].id")
                .isEqualTo("11111111-1111-1111-1111-111111111111")
                .jsonPath("$[0].active")
                .isEqualTo(true)
                .jsonPath("$[1].id")
                .isEqualTo("22222222-2222-2222-2222-222222222222")
                .jsonPath("$[1].active")
                .isEqualTo(true);
    }

    @Test
    void testUpdateActiveListNotFound() {
        webTestClient.patch()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                [
                    {
                        "id": "99999999-9999-9999-9999-999999999999",
                        "active": true
                    }
                ]
                """)
                .exchange()
                .expectStatus().isNotFound();
    }
}
