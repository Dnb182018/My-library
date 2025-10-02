package com.library.loans.dataacceslayer;


import com.library.loans.domainClientLayer.catalogs.CatalogModel;
import com.library.loans.domainClientLayer.fines.FineModel;
import com.library.loans.domainClientLayer.members.MemberModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "loans")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    private String id;

    @Indexed(unique = true)
    private LoanIdentifier loanIdentifier;

    private LocalDate loanDateStart;

    private LocalDate loanDateEnd;

    private LoanStatus loanStatus;


    private MemberModel memberModel;

    private CatalogModel catalogModel;

    private FineModel fineModel;

    public Loan( LocalDate loanDateStart, LocalDate loanDateEnd, LoanStatus loanStatus, MemberModel memberModel, CatalogModel catalogModel, FineModel fineModel) {
        this.loanIdentifier = new LoanIdentifier();
        this.loanDateStart = loanDateStart;
        this.loanDateEnd = loanDateEnd;
        this.loanStatus = loanStatus;
        this.memberModel = memberModel;
        this.catalogModel = catalogModel;
        this.fineModel = fineModel;
    }



}
