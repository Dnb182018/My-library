package com.library.apigateway.presentationlayer.Fine;

import com.library.apigateway.domainclientlayer.fines.FinePayment;
import com.library.apigateway.domainclientlayer.fines.Status;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FineResponseModel extends RepresentationModel<FineResponseModel> {

    private String fineId;
    private LocalDate issueDate;
    private Status status;
    private List<FinePayment> finePayment;

}
