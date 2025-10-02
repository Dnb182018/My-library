package com.library.apigateway.presentationlayer.loans;

import com.library.apigateway.domainclientlayer.loans.LoanStatus;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRequestModel {

    private LocalDate loanDateStart;
    private LocalDate loanDateEnd;
    private LoanStatus loanStatus;

    private String catalogId;
    private String bookId;

    private String fineId;
}
