package com.library.loans.presentationlayer;

import com.library.loans.dataacceslayer.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
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
