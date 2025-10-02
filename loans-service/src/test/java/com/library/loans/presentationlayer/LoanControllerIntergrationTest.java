package com.library.loans.presentationlayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.loans.dataacceslayer.LoanRepository;
import com.library.loans.dataacceslayer.LoanStatus;
import com.library.loans.domainClientLayer.catalogs.CatalogModel;
import com.library.loans.domainClientLayer.fines.FineModel;
import com.library.loans.domainClientLayer.members.MemberModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LoanControllerIntergrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private LoanRepository loanRepository;

    private MockRestServiceServer mockRestServiceServer;

    private ObjectMapper mapper = new ObjectMapper();

    private final String BASE_URI = "/api/v1/memberRecords/";
    private final String NOT_FOUND_LOAN_ID = "9f5d1e7c-6c3b-4a97-9b18-21e7a78e1300";
    private final String FOUND_LOAN_ID = "9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d1";
    private final String MEMBER_ID = "43b06e4a-c1c5-41d4-a716-446655440001";

    private final String BASE_URI_MEMBER = "http://members-service:8080/api/v1/memberRecords/";
    private final String BASE_URI_CATALOGS = "http://catalogs-service:8080/api/v1/catalogs/";
    private final String BASE_URI_FINES = "http://fines-service:8080/api/v1/fines/";


    @Autowired
    RestTemplate restTemplate;

    @BeforeEach
    void init() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }


    @Test
    void whenMemberIdExists_thenReturnAllLoans() throws JsonProcessingException, URISyntaxException {
        // Arrange: mock external call to MemberService
        var memberModel = MemberModel.builder()
                .memberId(MEMBER_ID)
                .userId("c195fd3a-3580-4b71-80f1-010000000000")
                .userFirstName("John")
                .userLastName("Doe")
                .build();

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI(BASE_URI_MEMBER + MEMBER_ID ))) // Adjust the base URI
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(memberModel)));

        // Act + Assert
        webTestClient.get()
                .uri(BASE_URI + MEMBER_ID + "/loans")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LoanResponseModel.class)
                .value(loans -> {
                    assertNotNull(loans);
                    assertFalse(loans.isEmpty());
                    assertEquals(MEMBER_ID, loans.get(0).getMemberId());
                });
    }


    @Test
    public void whenValidLoanRequest_thenCreateLoan() throws JsonProcessingException, URISyntaxException {
        // Arrange: mock external services
        var memberModel = MemberModel.builder()
                .memberId(MEMBER_ID)
                .userId("c195fd3a-3580-4b71-80f1-010000000000")
                .userFirstName("John")
                .userLastName("Doe")
                .build();

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI(BASE_URI_MEMBER + memberModel.getMemberId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(memberModel)));

        var catalogModel = CatalogModel.builder()
                .catalogId("3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0")
                .bookId("550e8400-e29b-41d4-a716-446655440001")
                .bookName("Clean Code")
                .build();

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI(BASE_URI_CATALOGS + catalogModel.getCatalogId()  + "/books/" + catalogModel.getBookId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(catalogModel)));

        var fineModel = FineModel.builder()
                .fineId("c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
                .fineStatus("PAID")
                .build();

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI(BASE_URI_FINES + fineModel.getFineId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(fineModel)));

        var request = buildSampleLoanRequestModel();

        // Act + Assert
        webTestClient.post()
                .uri(BASE_URI + MEMBER_ID + "/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LoanResponseModel.class)
                .value(response -> {
                    assertNotNull(response.getLoanId());
                    assertEquals(MEMBER_ID, response.getMemberId());
                    assertEquals(request.getLoanDateStart(), response.getLoanDateStart());
                    assertEquals(request.getLoanDateEnd(), response.getLoanDateEnd());
                    assertEquals(request.getLoanStatus(), response.getLoanStatus());
                    assertEquals(request.getBookId(), response.getBookId());
                    assertEquals(request.getFineId(), response.getFineId());
                });
    }






    @Test
    void whenNoLoansExist_thenReturnEmptyList() throws JsonProcessingException, URISyntaxException {
        // Arrange: clear all loans
        loanRepository.deleteAll();

        // Arrange: mock MemberService call
        var memberModel = MemberModel.builder()
                .memberId(MEMBER_ID)
                .userId("c195fd3a-3580-4b71-80f1-010000000000")
                .userFirstName("John")
                .userLastName("Doe")
                .build();

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI(BASE_URI_MEMBER + MEMBER_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(memberModel)));

        // Act & Assert
        webTestClient.get()
                .uri(BASE_URI + MEMBER_ID + "/loans")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(LoanResponseModel.class)
                .value(loans -> {
                    assertNotNull(loans);
                    assertTrue(loans.isEmpty());
                });
    }







    @Test
    public void whenMemberIdDoesNotExist_thenReturnNotFound() throws JsonProcessingException, URISyntaxException {
        // Arrange: mock external call to MemberService
        var missingMemberModel = MemberModel.builder()
                .memberId(NOT_FOUND_LOAN_ID) // or any non-existent UUID
                .userFirstName("Ghost")
                .userLastName("User")
                .build();

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI(BASE_URI_MEMBER + NOT_FOUND_LOAN_ID)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(missingMemberModel)));

        // Act + Assert
        webTestClient.get()
                .uri(BASE_URI + NOT_FOUND_LOAN_ID + "/loans")
                .exchange()
                .expectStatus().isNotFound();
    }






    private LoanRequestModel buildSampleLoanRequestModel() {
        return LoanRequestModel.builder()
                .loanDateStart(LocalDate.of(2024, 1, 15))
                .loanDateEnd(LocalDate.of(2024, 2, 15))
                .loanStatus(LoanStatus.RETURNED)
                .catalogId("3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0")
                .bookId("550e8400-e29b-41d4-a716-446655440001")
                .fineId("c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
                .build();
    }



}