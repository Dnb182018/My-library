package com.library.loans.dataacceslayer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LoanIdentifier {

    @Indexed(unique = true)
    private String loanId;

    public LoanIdentifier() {
        this.loanId = UUID.randomUUID().toString();
    }


}
