package com.library.catalogs.datalayer.Catalog;


import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "catalog")
@Data
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private CatalogIdentifier catalogIdentifier;

    @Enumerated(EnumType.STRING)
    private CatalogLocations location;

    public Catalog(){
        this.catalogIdentifier = new CatalogIdentifier();
    }

    public Catalog(@NotNull CatalogLocations location){
        this.catalogIdentifier = new CatalogIdentifier();
        this.location = location;
    }

    public String getCatalogs(){
        return catalogIdentifier.getCatalogId();
    }


}
