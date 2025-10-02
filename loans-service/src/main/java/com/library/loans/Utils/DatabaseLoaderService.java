package com.library.loans.Utils;


import com.library.loans.dataacceslayer.Loan;
import com.library.loans.dataacceslayer.LoanIdentifier;
import com.library.loans.dataacceslayer.LoanRepository;
import com.library.loans.dataacceslayer.LoanStatus;
import com.library.loans.domainClientLayer.catalogs.CatalogModel;
import com.library.loans.domainClientLayer.fines.FineModel;
import com.library.loans.domainClientLayer.members.MemberModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseLoaderService implements CommandLineRunner {


    @Autowired
    LoanRepository loanRepository;


    @Override
    public void run(String... args) throws Exception {
        // Member Models
        MemberModel memberModel1 = MemberModel.builder()
                .memberId("43b06e4a-c1c5-41d4-a716-446655440001")
                .userId("c195fd3a-3580-4b71-80f1-010000000000")
                .firstName("John")
                .lastName("Doe")
                .build();

        MemberModel memberModel2 = MemberModel.builder()
                .memberId("43b06e4a-c1c5-41d4-a716-446655440002")
                .userId("c195fd3a-3580-4b71-80f1-020000000000")
                .firstName("Jane")
                .lastName("Smith")
                .build();

        // Book Models
        CatalogModel bookModel1 = CatalogModel.builder()
                .bookId("550e8400-e29b-41d4-a716-446655440003")
                .catalogId("3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0")
                .title("The Hobbit")
                .build();

        CatalogModel bookModel2 = CatalogModel.builder()
                .bookId("550e8400-e29b-41d4-a716-446655440002")
                .catalogId("8f7a9a5f-5106-423a-97c7-2f905dcdffb9")
                .title("Brave New World")
                .build();

        // Fine Models
        FineModel fineModel1 = FineModel.builder()
                .fineId("c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
                .status("PAID")
                .build();

        FineModel fineModel2 = FineModel.builder()
                .fineId("b8c9d0e1-f2a3-4b5c-6d7e-8f9a0b1c2d3e")
                .status("UnPAID")
                .build();

        // Loan Models
        Loan loan1 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d1"))
                .loanDateStart(LocalDate.of(2024, 01, 15))
                .loanDateEnd(LocalDate.of(2024, 02, 15))
                .loanStatus(LoanStatus.RETURNED)
                .catalogModel(bookModel1)
                .memberModel(memberModel1)
                .fineModel(fineModel1)
                .build();

        Loan loan2 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d2"))
                .loanDateStart(LocalDate.of(2024, 02, 01))
                .loanDateEnd(LocalDate.of(2024, 03, 01))
                .loanStatus(LoanStatus.ACTIVES)
                .catalogModel(bookModel2)
                .memberModel(memberModel1)
                .fineModel(fineModel2)
                .build();

        Loan loan3 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d3"))
                .loanDateStart(LocalDate.of(2024, 03, 10))
                .loanDateEnd(LocalDate.of(2024, 04, 10))
                .loanStatus(LoanStatus.OVERDUE)
                .catalogModel(bookModel1)
                .memberModel(memberModel2)
                .fineModel(fineModel1)
                .build();

        Loan loan4 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d4"))
                .loanDateStart(LocalDate.of(2024, 01, 05))
                .loanDateEnd(LocalDate.of(2024, 02, 05))
                .loanStatus(LoanStatus.ACTIVES)
                .catalogModel(bookModel2)
                .memberModel(memberModel2)
                .fineModel(fineModel2)
                .build();

        Loan loan5 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d5"))
                .loanDateStart(LocalDate.of(2024, 02, 20))
                .loanDateEnd(LocalDate.of(2024, 03, 20))
                .loanStatus(LoanStatus.LOST)
                .catalogModel(bookModel1)
                .memberModel(memberModel1)
                .fineModel(fineModel1)
                .build();

        Loan loan6 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d6"))
                .loanDateStart(LocalDate.of(2024, 03, 01))
                .loanDateEnd(LocalDate.of(2024, 04, 01))
                .loanStatus(LoanStatus.LOST)
                .catalogModel(bookModel2)
                .memberModel(memberModel2)
                .fineModel(fineModel2)
                .build();

        Loan loan7 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d7"))
                .loanDateStart(LocalDate.of(2024, 01, 10))
                .loanDateEnd(LocalDate.of(2024, 02, 10))
                .loanStatus(LoanStatus.ACTIVES)
                .catalogModel(bookModel1)
                .memberModel(memberModel1)
                .fineModel(fineModel1)
                .build();

        Loan loan8 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d8"))
                .loanDateStart(LocalDate.of(2024, 02, 15))
                .loanDateEnd(LocalDate.of(2024, 03, 15))
                .loanStatus(LoanStatus.OVERDUE)
                .catalogModel(bookModel2)
                .memberModel(memberModel2)
                .fineModel(fineModel2)
                .build();

        Loan loan9 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d9"))
                .loanDateStart(LocalDate.of(2024, 03, 01))
                .loanDateEnd(LocalDate.of(2024, 04, 01))
                .loanStatus(LoanStatus.ACTIVES)
                .catalogModel(bookModel1)
                .memberModel(memberModel1)
                .fineModel(fineModel1)
                .build();

        Loan loan10 = Loan.builder()
                .loanIdentifier(new LoanIdentifier("9f5d1e7c-6c3b-4a97-9b18-22e7a78e13d1"))
                .loanDateStart(LocalDate.of(2024, 01, 25))
                .loanDateEnd(LocalDate.of(2024, 02, 25))
                .loanStatus(LoanStatus.RETURNED)
                .catalogModel(bookModel2)
                .memberModel(memberModel2)
                .fineModel(fineModel2)
                .build();

        // Save loans to repository
        loanRepository.save(loan1);
        loanRepository.save(loan2);
        loanRepository.save(loan3);
        loanRepository.save(loan4);
        loanRepository.save(loan5);
        loanRepository.save(loan6);
        loanRepository.save(loan7);
        loanRepository.save(loan8);
        loanRepository.save(loan9);
        loanRepository.save(loan10);

    }
}




