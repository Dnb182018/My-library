package com.library.fines.datalayer;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "fine")
@Data
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private FineIdentifier fineIdentifier;

    @Column(name = "issue_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issueDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "fine_payment", joinColumns = @JoinColumn(name = "fine_id"))
    private List<FinePayment> finePaymentList;

    public Fine(){
        this.fineIdentifier = new FineIdentifier();
    }

    public Fine( LocalDate issueDate, Status status, List<FinePayment> finePaymentList) {
        this.fineIdentifier = new FineIdentifier();
        this.issueDate = issueDate;
        this.status = status;
        this.finePaymentList = finePaymentList;
    }
}
