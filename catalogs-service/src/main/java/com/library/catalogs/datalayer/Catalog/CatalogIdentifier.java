package com.library.catalogs.datalayer.Catalog;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class CatalogIdentifier {

    private String catalogId;

    public CatalogIdentifier(){
        this.catalogId = UUID.randomUUID().toString();
        System.out.println("Generated UUID: " + this.catalogId);
    }

    public CatalogIdentifier(String catalogId){
        this.catalogId = catalogId;
    }
}

