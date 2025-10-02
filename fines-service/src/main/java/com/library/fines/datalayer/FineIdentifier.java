package com.library.fines.datalayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class FineIdentifier {
    private String fineId;

    public FineIdentifier(){
        this.fineId = UUID.randomUUID().toString();
        System.out.println("Generated UUID: " + this.fineId);
    }

    public FineIdentifier(String fineId){
        this.fineId = fineId;
    }
}
