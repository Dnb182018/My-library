package com.library.loans.dataacceslayer;

import com.library.loans.domainClientLayer.catalogs.CatalogModel;
import com.library.loans.domainClientLayer.fines.FineModel;
import com.library.loans.domainClientLayer.members.MemberModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
@ActiveProfiles("test")
class LoanRepositoryIntergrationTest {

    @Autowired
    private LoanRepository loanRepository;

    @BeforeEach
    public void setupDb(){
        loanRepository.deleteAll();
    }
    @Test
    public void whenMemberIdExists_ReturnLoans() {
        //arrange
        Loan loan1 = LoanBuilder();
        Loan loan2 = LoanBuilder();

        //act
        loanRepository.save(loan1);
        loanRepository.save(loan2);

        List<Loan> loans = loanRepository.findAllByMemberModel_MemberId("43b06e4a-c1c5-41d4-a716-446655440001");

        //assert
        assertNotNull(loans);
        assertEquals(2, loans.size());
    }

    @Test
    public void whenMemberIdDoesNotExists_ReturnEmptyList() {
        //arrange
        Loan loan1 = LoanBuilder();
        Loan loan2 = LoanBuilder();

        //act
        loanRepository.save(loan1);
        loanRepository.save(loan2);

        List<Loan> loans = loanRepository.findAllByMemberModel_MemberId("43b06e4a-c1c5-41d4-a716-446655440002");

        //assert
        assertNotNull(loans);
        assertEquals(0, loans.size());
    }

    @Test
    public void whenLoanIdAndMemberIdExists_ReturnLoan() {
        //arrange
        Loan loan1 = LoanBuilder();

        //act
        loanRepository.save(loan1);

        Loan loanFound = loanRepository.findByMemberModel_MemberIdAndLoanIdentifier_LoanId(loan1.getMemberModel().getMemberId(),loan1.getLoanIdentifier().getLoanId());

        //assert
        assertNotNull(loanFound);
        assertEquals(loan1.getLoanIdentifier().getLoanId(), loanFound.getLoanIdentifier().getLoanId());
    }

    @Test
    public void whenSavingNewLoan_thenLoanIsPersisted() {
        // Arrange
        Loan loan = LoanBuilder();

        // Act
        Loan savedLoan = loanRepository.save(loan);

        // Assert
        assertNotNull(savedLoan);
        assertEquals(loan.getLoanIdentifier().getLoanId(), savedLoan.getLoanIdentifier().getLoanId());
    }

    @Test
    public void whenUpdatingLoanStatus_thenStatusIsUpdated() {
        // Arrange
        Loan loan = LoanBuilder();
        loanRepository.save(loan);

        // Act
        loan.setLoanStatus(LoanStatus.RETURNED);
        loanRepository.save(loan); // Save again to update

        Loan updatedLoan = loanRepository.findByMemberModel_MemberIdAndLoanIdentifier_LoanId(
                loan.getMemberModel().getMemberId(),
                loan.getLoanIdentifier().getLoanId()
        );

        // Assert
        assertNotNull(updatedLoan);
        assertEquals(LoanStatus.RETURNED, updatedLoan.getLoanStatus());
    }


    @Test
    public void whenDeletingLoan_thenLoanIsRemoved() {
        // Arrange
        Loan loan = LoanBuilder();
        loanRepository.save(loan);

        // Act
        loanRepository.delete(loan);
        Loan deletedLoan = loanRepository.findByMemberModel_MemberIdAndLoanIdentifier_LoanId(
                loan.getMemberModel().getMemberId(),
                loan.getLoanIdentifier().getLoanId()
        );

        // Assert
        assertNull(deletedLoan);
    }






    //--------------------------------------------------
    //NEGATIVE


    @Test
    public void whenLoanIdAndMemberIdDoesNotExists_ReturnNull() {
        //arrange
        Loan loan1 = LoanBuilder();

        //act
        loanRepository.save(loan1);

        Loan loanFound = loanRepository.findByMemberModel_MemberIdAndLoanIdentifier_LoanId("nonexistent-member-id","nonexistent-loan-id");

        //assert
        assertNull(loanFound);
    }












    public Loan LoanBuilder() {
        // Create LoanIdentifier
        LoanIdentifier loanIdentifier = new LoanIdentifier();

        // Create CatalogModel
        CatalogModel catalogModel = CatalogModel.builder()
                .bookId("550e8400-e29b-41d4-a716-446655440001")
                .catalogId("3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0")
                .bookName("The Hobbit")
                .build();

        // Create MemberModel (with passed memberId)
        MemberModel memberModel = MemberModel.builder()
                .memberId("43b06e4a-c1c5-41d4-a716-446655440001")
                .userId("c195fd3a-3580-4b71-80f1-010000000000")
                .userFirstName("John")
                .userLastName("Doe")
                .build();

        // Create FineModel
        FineModel fineModel = FineModel.builder()
                .fineId("c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
                .fineStatus("PAID")
                .build();

        // Create Loan
        return Loan.builder()
                .loanIdentifier(loanIdentifier)
                .loanDateStart(LocalDate.of(2024, 01, 15))
                .loanDateEnd(LocalDate.of(2024, 02, 15))
                .loanStatus(LoanStatus.RETURNED)
                .catalogModel(catalogModel)
                .memberModel(memberModel)
                .fineModel(fineModel)
                .build();

    }

}




