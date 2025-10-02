package com.library.apigateway.presentationlayer.loans;


import com.library.apigateway.domainclientlayer.loans.LoanStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanResponseModel extends RepresentationModel<LoanResponseModel> {

    private String loanId;
    private LocalDate loanDateStart;
    private LocalDate loanDateEnd;
    private LoanStatus loanStatus;

    private String bookId;
    private String title;

    private String memberId;
    private String userId;
    private String firstName;
    private String lastName;

    private String fineId;
    private String status;
}
