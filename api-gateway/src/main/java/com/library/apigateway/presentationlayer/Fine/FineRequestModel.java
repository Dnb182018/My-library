package com.library.apigateway.presentationlayer.Fine;

import com.library.apigateway.domainclientlayer.fines.FinePayment;
import com.library.apigateway.domainclientlayer.fines.Status;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FineRequestModel {

    private LocalDate issueDate;
    private Status status;
    private List<FinePayment> finePayment;
}
