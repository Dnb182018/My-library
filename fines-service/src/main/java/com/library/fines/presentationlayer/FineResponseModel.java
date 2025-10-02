package com.library.fines.presentationlayer;

import com.library.fines.datalayer.FinePayment;
import com.library.fines.datalayer.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FineResponseModel extends RepresentationModel<FineResponseModel> {

    private String fineId;
    private LocalDate issueDate;
    private Status status;
    private List<FinePayment> finePayment;

}
