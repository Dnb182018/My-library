package com.library.apigateway.presentationlayer.Catalog;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogResponseModel extends RepresentationModel<CatalogResponseModel> {

    private String catalogId;
    private String location;

}
