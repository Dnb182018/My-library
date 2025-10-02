package com.library.fines.presentationlayer;

import com.library.fines.datalayer.FinePayment;
import com.library.fines.datalayer.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FineRequestModel {

    private LocalDate issueDate;
    private Status status;
    private List<FinePayment> finePayment;
}
