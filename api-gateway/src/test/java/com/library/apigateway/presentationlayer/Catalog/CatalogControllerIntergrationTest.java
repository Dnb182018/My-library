package com.library.apigateway.presentationlayer.Catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CatalogControllerIntergrationTest {

    private final String BASE_URL_CATALOGS = "api/v1/catalogs";



    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldForwardRequestToCatalogsService() {
        webTestClient.get()
                .uri(BASE_URL_CATALOGS)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void whenCreateCatalogWithInvalidPayload_thenReturnBadRequest() {
        CatalogRequestModel invalidCatalog = new CatalogRequestModel(); // missing required `location`

        webTestClient.post()
                .uri(BASE_URL_CATALOGS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(invalidCatalog)
                .exchange()
                .expectStatus().isBadRequest();
    }

}