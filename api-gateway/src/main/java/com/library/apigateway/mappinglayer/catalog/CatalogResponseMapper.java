package com.library.apigateway.mappinglayer.catalog;

import com.library.apigateway.presentationlayer.Catalog.CatalogController;
import com.library.apigateway.presentationlayer.Catalog.CatalogResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CatalogResponseMapper {

    CatalogResponseModel entityToResponseModel(CatalogResponseModel catalogResponseModel);

    List<CatalogResponseModel> entityListToResponseModelList(List<CatalogResponseModel> catalogResponseModels);


   @AfterMapping
    default void addLinks(@MappingTarget CatalogResponseModel catalogResponseModel){
        //Catalog
        Link catalogLink  = linkTo(methodOn(CatalogController.class)
                .getCatalogById(catalogResponseModel.getCatalogId()))
                .withSelfRel();
        catalogResponseModel.add(catalogLink);


        //All catalogs
        Link allCatalogs = linkTo(methodOn(CatalogController.class)
                .getAllCatalogs())
                .withRel("allCatalogs");
        catalogResponseModel.add(allCatalogs);


    }

}
