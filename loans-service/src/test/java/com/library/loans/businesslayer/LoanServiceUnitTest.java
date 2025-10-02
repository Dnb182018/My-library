package com.library.loans.businesslayer;

import com.library.loans.Utils.exceptions.InvalidInputException;
import com.library.loans.Utils.exceptions.NotFoundException;
import com.library.loans.dataacceslayer.Loan;
import com.library.loans.dataacceslayer.LoanIdentifier;
import com.library.loans.dataacceslayer.LoanRepository;
import com.library.loans.dataacceslayer.LoanStatus;
import com.library.loans.domainClientLayer.catalogs.CatalogModel;
import com.library.loans.domainClientLayer.catalogs.CatalogsServiceClient;
import com.library.loans.domainClientLayer.fines.FineModel;
import com.library.loans.domainClientLayer.fines.FineServiceClient;
import com.library.loans.domainClientLayer.members.MemberModel;
import com.library.loans.domainClientLayer.members.MembersServiceClient;
import com.library.loans.mappinglayer.LoanRequestMapper;
import com.library.loans.mappinglayer.LoanResponseMapper;
import com.library.loans.presentationlayer.LoanRequestModel;
import com.library.loans.presentationlayer.LoanResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration")
@ActiveProfiles("test")
class LoanServiceUnitTest {

    @Autowired
    LoanService loanService;

    @MockitoBean
    MembersServiceClient memberServiceClient;

    @MockitoBean
    CatalogsServiceClient catalogServiceClient;

    @MockitoBean
    FineServiceClient fineServiceClient;

    @MockitoBean
    LoanRepository loanRepository;

    @MockitoSpyBean
    LoanRequestMapper loanRequestMapper;

    @MockitoSpyBean
    LoanResponseMapper loanResponseMapper;

    private final String memberId= "43b06e4a-c1c5-41d4-a716-446655440001";
    private final String loanId= "9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d2";

    @Test
    public void whenValidMemberId_CatalogId_FineId_thenProcessMemberLoanRequest(){
        //arrange
        var memberModel1 = MemberModel.builder()
                .memberId("43b06e4a-c1c5-41d4-a716-446655440001")
                .userId("c195fd3a-3580-4b71-80f1-010000000000")
                .userFirstName("John")
                .userLastName("Doe")
                .build();

        var fineModel1 = FineModel.builder()
                .fineId("c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
                .fineStatus("PAID")
                .build();

        var bookModel1 = CatalogModel.builder()
                .bookId("550e8400-e29b-41d4-a716-446655440003")
                .catalogId("3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0")
                .bookName("The Hobbit")
                .build();


        var loanRequestModel  = LoanRequestModel.builder()
                .loanDateStart(LocalDate.of(2024, 01, 15))
                .loanDateEnd(LocalDate.of(2024, 02, 15))
                .loanStatus(LoanStatus.RETURNED)
                .catalogId("3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0")
                .bookId("550e8400-e29b-41d4-a716-446655440003")
                .fineId("c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
                .build();

        var LoanIdentifier = new LoanIdentifier();

        var loan1 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d1"))
                .loanDateStart(LocalDate.of(2024, 01, 15))
                .loanDateEnd(LocalDate.of(2024, 02, 15))
                .loanStatus(LoanStatus.RETURNED)
                .catalogModel(bookModel1)
                .memberModel(memberModel1)
                .fineModel(fineModel1)
                .build();



        //define mock behavior

        when(memberServiceClient.getMemberByMemberId(memberModel1.getMemberId())).thenReturn(memberModel1);
        when(catalogServiceClient.getBooksById(bookModel1.getCatalogId(),bookModel1.getBookId())).thenReturn(bookModel1);
        when(fineServiceClient.getFineByID(fineModel1.getFineId())).thenReturn(fineModel1);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan1);


        //act
        LoanResponseModel loanResponseModel = loanService.newLoan(loanRequestModel,memberModel1.getMemberId());

        //assert
        assertNotNull(loanRequestModel);
        assertNotNull(loanResponseModel);
        assertEquals(bookModel1.getBookId(),loanResponseModel.getBookId());
        assertEquals(bookModel1.getBookName(),loanResponseModel.getBookName());
        assertEquals(memberModel1.getUserId(), loanResponseModel.getUserId());
        assertEquals(memberModel1.getUserFirstName(), loanResponseModel.getUserFirstName());
        assertEquals(memberModel1.getUserLastName(), loanResponseModel.getUserLastName());
        assertEquals(fineModel1.getFineId(), loanResponseModel.getFineId());
        assertEquals(fineModel1.getFineStatus(),loanResponseModel.getFineStatus());
        assertEquals(loanRequestModel.getLoanDateStart(), loanResponseModel.getLoanDateStart());
        assertEquals(loanRequestModel.getLoanDateEnd(), loanResponseModel.getLoanDateEnd());
        assertEquals(loanRequestModel.getLoanStatus(), loanResponseModel.getLoanStatus());


        //for the spies
        verify(loanResponseMapper, times(1)).entityToResponseModel(loan1);
    }


    @Test
    public void whenValidMemberId_CatalogId_FineId_thenReturnsLoan() {
        // Arrange
        var loanIdentifier = new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d1");

        var memberModel = MemberModel.builder()
                .memberId("43b06e4a-c1c5-41d4-a716-446655440001")
                .userId("c195fd3a-3580-4b71-80f1-010000000000")
                .userFirstName("John")
                .userLastName("Doe")
                .build();

        var fineModel = FineModel.builder()
                .fineId("c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
                .fineStatus("PAID")
                .build();

        var catalogModel = CatalogModel.builder()
                .bookId("550e8400-e29b-41d4-a716-446655440003")
                .catalogId("3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0")
                .bookName("The Hobbit")
                .build();

        var loan = Loan.builder()
                .loanIdentifier(loanIdentifier)
                .loanDateStart(LocalDate.of(2024, 1, 15))
                .loanDateEnd(LocalDate.of(2024, 2, 15))
                .loanStatus(LoanStatus.RETURNED)
                .catalogModel(catalogModel)
                .memberModel(memberModel)
                .fineModel(fineModel)
                .build();

        // Define mock behavior
        when(loanRepository.findByMemberModel_MemberIdAndLoanIdentifier_LoanId(
                 memberModel.getMemberId(),loanIdentifier.getLoanId()))
                .thenReturn(loan);

        when(memberServiceClient.getMemberByMemberId(memberModel.getMemberId()))
                .thenReturn(memberModel);

        when(catalogServiceClient.getBooksById(catalogModel.getCatalogId(), catalogModel.getBookId()))
                .thenReturn(catalogModel);

        when(fineServiceClient.getFineByID(fineModel.getFineId()))
                .thenReturn(fineModel);

        // Act
        LoanResponseModel result = loanService.getMemberLoanById(
                memberModel.getMemberId(),loanIdentifier.getLoanId());

        // Assert
        assertNotNull(result);
        assertEquals(catalogModel.getBookId(), result.getBookId());
        assertEquals(catalogModel.getBookName(), result.getBookName());
        assertEquals(memberModel.getUserId(), result.getUserId());
        assertEquals(memberModel.getUserFirstName(), result.getUserFirstName());
        assertEquals(memberModel.getUserLastName(), result.getUserLastName());
        assertEquals(fineModel.getFineId(), result.getFineId());
        assertEquals(fineModel.getFineStatus(), result.getFineStatus());
        assertEquals(loan.getLoanDateStart(), result.getLoanDateStart());
        assertEquals(loan.getLoanDateEnd(), result.getLoanDateEnd());
        assertEquals(loan.getLoanStatus(), result.getLoanStatus());
    }



    @Test
    public void whenValidLoanRequest_thenReturnCreatedLoan() {
        // Arrange
        var memberId = "43b06e4a-c1c5-41d4-a716-446655440001";
        var catalogId = "3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0";
        var bookId = "550e8400-e29b-41d4-a716-446655440003";
        var fineId = "c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f";

        var loanRequestModel = LoanRequestModel.builder()
                .loanDateStart(LocalDate.of(2024, 1, 15))
                .loanDateEnd(LocalDate.of(2024, 2, 15))
                .loanStatus(LoanStatus.RETURNED)
                .catalogId(catalogId)
                .bookId(bookId)
                .fineId(fineId)
                .build();

        var memberModel = MemberModel.builder()
                .memberId(memberId)
                .userId("c195fd3a-3580-4b71-80f1-010000000000")
                .userFirstName("John")
                .userLastName("Doe")
                .build();

        var catalogModel = CatalogModel.builder()
                .catalogId(catalogId)
                .bookId(bookId)
                .bookName("The Hobbit")
                .build();

        var fineModel = FineModel.builder()
                .fineId(fineId)
                .fineStatus("PAID")
                .build();

        var loanIdentifier = new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d1");

        var loan = Loan.builder()
                .loanIdentifier(loanIdentifier)
                .loanDateStart(loanRequestModel.getLoanDateStart())
                .loanDateEnd(loanRequestModel.getLoanDateEnd())
                .loanStatus(LoanStatus.RETURNED)
                .catalogModel(catalogModel)
                .memberModel(memberModel)
                .fineModel(fineModel)
                .build();

        var loanResponseModel = LoanResponseModel.builder()
                .loanId(loanIdentifier.getLoanId())
                .bookId(bookId)
                .bookName("The Hobbit")
                .memberId(memberId)
                .userId(memberModel.getUserId())
                .userFirstName("John")
                .userLastName("Doe")
                .fineId(fineId)
                .fineStatus("PAID")
                .loanDateStart(loanRequestModel.getLoanDateStart())
                .loanDateEnd(loanRequestModel.getLoanDateEnd())
                .loanStatus(LoanStatus.RETURNED)
                .build();

        // Mock behavior
        when(memberServiceClient.getMemberByMemberId(memberId)).thenReturn(memberModel);
        when(catalogServiceClient.getBooksById(catalogId, bookId)).thenReturn(catalogModel);
        when(fineServiceClient.getFineByID(fineId)).thenReturn(fineModel);
        when(loanRequestMapper.requestModelToEntity(any(), any(), any(), any(), any())).thenReturn(loan);
        when(loanRepository.save(any())).thenReturn(loan);
        when(loanResponseMapper.entityToResponseModel(loan)).thenReturn(loanResponseModel);

        // Act
        LoanResponseModel result = loanService.newLoan(loanRequestModel, memberId);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getBookId());
        assertEquals("The Hobbit", result.getBookName());
        assertEquals(memberModel.getUserId(), result.getUserId());
        assertEquals("John", result.getUserFirstName());
        assertEquals("Doe", result.getUserLastName());
        assertEquals(fineId, result.getFineId());
        assertEquals("PAID", result.getFineStatus());
        assertEquals(loanRequestModel.getLoanDateStart(), result.getLoanDateStart());
        assertEquals(loanRequestModel.getLoanDateEnd(), result.getLoanDateEnd());
        assertEquals("RETURNED", result.getLoanStatus().toString());
    }


    @Test
    public void whenValidLoanIdAndMemberId_thenDeletesLoan() {
        // Arrange
        var loanIdentifier = new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d1");

        var memberModel = MemberModel.builder()
                .memberId("43b06e4a-c1c5-41d4-a716-446655440001")
                .userId("c195fd3a-3580-4b71-80f1-010000000000")
                .userFirstName("John")
                .userLastName("Doe")
                .build();

        var fineModel = FineModel.builder()
                .fineId("c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
                .fineStatus("PAID")
                .build();

        var catalogModel = CatalogModel.builder()
                .bookId("550e8400-e29b-41d4-a716-446655440003")
                .catalogId("3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0")
                .bookName("The Hobbit")
                .build();

        var loan = Loan.builder()
                .loanIdentifier(loanIdentifier)
                .loanDateStart(LocalDate.of(2024, 1, 15))
                .loanDateEnd(LocalDate.of(2024, 2, 15))
                .loanStatus(LoanStatus.RETURNED)
                .catalogModel(catalogModel)
                .memberModel(memberModel)
                .fineModel(fineModel)
                .build();

        // Define mock behavior
        when(loanRepository.findByMemberModel_MemberIdAndLoanIdentifier_LoanId(
                memberModel.getMemberId(),loanIdentifier.getLoanId()))
                .thenReturn(loan);

        when(memberServiceClient.getMemberByMemberId(memberModel.getMemberId()))
                .thenReturn(memberModel);

        when(fineServiceClient.getFineByID(fineModel.getFineId()))
                .thenReturn(fineModel);

        when(catalogServiceClient.getBooksById(catalogModel.getCatalogId(), catalogModel.getBookId()))
                .thenReturn(catalogModel);

        // Act
        loanService.deleteLoan( memberModel.getMemberId(),loanIdentifier.getLoanId());

        // Assert
        verify(loanRepository, times(1)).delete(loan);
    }
//--------------------------------------------------------
    //NEGATIVE
    @Test
    void whenMemberNotFound_thenThrowNotFoundException() {
        // Arrange
        String invalidMemberId = "non-existent-member-id";
        LoanRequestModel loanRequest = LoanRequestModel.builder()
                .catalogId("catalog-id")
                .bookId("book-id")
                .fineId("fine-id")
                .loanDateStart(LocalDate.now())
                .loanDateEnd(LocalDate.now().plusDays(14))
                .loanStatus(LoanStatus.ACTIVES)
                .build();

        when(memberServiceClient.getMemberByMemberId(invalidMemberId))
                .thenThrow(new NotFoundException("Member not found"));

        // Act & Assert
        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            loanService.newLoan(loanRequest, invalidMemberId);
        });

        assertEquals("Member not found", ex.getMessage());
    }




}


