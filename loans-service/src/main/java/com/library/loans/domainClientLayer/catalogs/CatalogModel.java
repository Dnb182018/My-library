package com.library.loans.domainClientLayer.catalogs;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
public class CatalogModel {

    String catalogId;
    String bookId;
    String title;
}
