package com.library.fines.presentationlayer;


import com.library.fines.datalayer.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/data-h2.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FineControllerIntergrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private final String BASE_URI = "/api/v1/fines";
    private final String VALID_FINE_ID = "b3a9b040-1c2e-4c3b-9127-9f6a001a7c2b";
    private final String NOT_FOUND_FINE_ID = "b3a9b040-1c2e-4c3b-9127-9f6a001a7c25";
    private final String INVALID_FINE_ID = "invalid-id-123";

    //GET All
    @Test
    void whenFinesExist_thenReturnAllFines() {
        webTestClient.get()
                .uri(BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(FineResponseModel.class)
                .value(fines -> {
                    assertNotNull(fines);
                    assertFalse(fines.isEmpty());
                });
    }

    //ID
    @Test
    void whenValidFineId_thenReturnFine() {
        webTestClient.get()
                .uri(BASE_URI + "/" + VALID_FINE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FineResponseModel.class)
                .value(fine -> {
                    assertNotNull(fine);
                    assertEquals("PAID", fine.getStatus().toString());
                });
    }

    //NEW
    @Test
    public void whenFineRequestIsValid_thenReturnNewFine() {
        //arrange
        FineRequestModel fineRequestModel = buildSampleFine();

        //act and assert
        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(fineRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(FineResponseModel.class)
                .value((fineResponseModel) -> {
                    assertNotNull(fineResponseModel);
                    assertNotNull(fineResponseModel.getFineId());
                    assertEquals(fineRequestModel.getStatus(), fineResponseModel.getStatus());
                });

    }


    @Test
    public void whenFineRequestIsValid_thenReturnUpdatedFine() {
        // Arrange
        FineRequestModel fineRequestModel = buildSampleFine();
        String fineId = VALID_FINE_ID;  // Make sure this is a valid fineId that exists in your test data

        // Act and assert
        webTestClient.put()
                .uri(BASE_URI + "/" + fineId)  // Use the fineId in the URI for update
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(fineRequestModel)
                .exchange()
                .expectStatus().isOk()  // Expecting HTTP 200 OK for a successful update
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(FineResponseModel.class)
                .value((fineResponseModel) -> {
                    assertNotNull(fineResponseModel);
                    assertNotNull(fineResponseModel.getFineId());
                    assertEquals(fineRequestModel.getStatus(), fineResponseModel.getStatus());
                    // Additional assertions if needed, e.g., checking other properties of the response
                });
    }



    @Test
    void whenFineDeleted_thenReturnNoContent() {
        webTestClient.delete()
                .uri(BASE_URI + "/" + VALID_FINE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void whenFineCreated_thenCanBeRetrievedById() {
        // Arrange
        FineRequestModel fineRequestModel = buildSampleFine();

        // Act
        FineResponseModel created = webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(fineRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FineResponseModel.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(created);
        String newFineId = created.getFineId();

        // Assert: Retrieve by ID
        webTestClient.get()
                .uri(BASE_URI + "/" + newFineId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FineResponseModel.class)
                .value(fetched -> {
                    assertEquals(newFineId, fetched.getFineId());
                    assertEquals(fineRequestModel.getStatus(), fetched.getStatus());
                });
    }


    @Test
    void whenNullPaymentList_thenFineIsCreatedSuccessfully() {
        FineRequestModel fine = FineRequestModel.builder()
                .issueDate(LocalDate.parse("2025-03-01"))
                .status(Status.UNPAID)
                .finePayment(null)
                .build();

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(fine)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FineResponseModel.class)
                .value(response -> {
                    assertEquals(Status.UNPAID, response.getStatus());
                    assertNull(response.getFinePayment());
                });
    }




    //_____________________________NEGATIVE_________________________

    @Test
    public void whenInvalidEnumStatus_thenReturnBadRequest() {
        String invalidJson = """
        {
            "issueDate": "2025-01-01",
            "status": "INVALID_STATUS",
            "finePayment": [{
                "amount": 100,
                "currency": "USD",
                "paymentDate": "2025-01-02",
                "paymentStatus": "COMPLETE",
                "paymentMethod": "CREDIT"
            }]
        }
        """;

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidJson)
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    void whenNoFinesExist_thenReturnEmptyList() {
        // Assume no fines are in the database at the start
        webTestClient.get()
                .uri(BASE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FineResponseModel.class)
                .value(fines -> assertFalse(fines.isEmpty())); // Check that the list is empty
    }


    @Test
    public void whenFineIsNotExistsOnDelete_thenReturnNotFound() {
        webTestClient.delete()
                .uri(BASE_URI + "/" + NOT_FOUND_FINE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound(); ///isNotFound
    }



    @Test
    public void whenNotValidIssueDateOnUpdate_thenThrowInvalidInputExceptions(){
        //arrange
        FineRequestModel fineRequestModel = buildSampleFine();
        fineRequestModel.setStatus(Status.PAID);
        fineRequestModel.setIssueDate(LocalDate.parse("3000-01-18"));
        fineRequestModel.setFinePayment(Arrays.asList(new FinePayment(BigDecimal.valueOf(150.00),
                Currency.USD,
                LocalDate.parse("3000-01-18"),
                PaymentStatus.COMPLETE,
                PaymentMethod.CREDIT)));

        //act
        webTestClient.put()
                .uri(BASE_URI + "/" + VALID_FINE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(fineRequestModel)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

    }



    @Test
    public void whenFineIdIsInvalid_thenReturnNotFoundOnUpdate() {

      FineRequestModel fineRequestModel = buildSampleFine();

      webTestClient.put()
              .uri(BASE_URI + "/" + INVALID_FINE_ID)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .bodyValue(fineRequestModel)
              .exchange()
              .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

    }
    @Test
    public void whenFineIdIsInvalidOnDelete_ThenReturnUNPROCESSABLE_ENTITY() {

        webTestClient.delete()
                .uri(BASE_URI + "/" + INVALID_FINE_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }








        private FineRequestModel buildSampleFine() {
        return FineRequestModel.builder()
                .issueDate(LocalDate.parse("2025-01-15"))
                .status(Status.PAID)
                .finePayment(Arrays.asList(new FinePayment(BigDecimal.valueOf(150.00),
                                Currency.USD,
                                LocalDate.parse("2025-01-18"),
                                PaymentStatus.COMPLETE,
                                PaymentMethod.CREDIT)))
                .build();
    }


}

