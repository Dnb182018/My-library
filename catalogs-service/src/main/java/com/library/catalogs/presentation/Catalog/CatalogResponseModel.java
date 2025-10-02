package com.library.catalogs.presentation.Catalog;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogResponseModel extends RepresentationModel<CatalogResponseModel> {

    private String catalogId;
    private String location;

}
