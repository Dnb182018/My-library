package com.library.catalogs.mappinglayer.Catalog;

import com.library.catalogs.datalayer.Catalog.Catalog;
import com.library.catalogs.presentation.Catalog.CatalogController;
import com.library.catalogs.presentation.Catalog.CatalogResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface CatalogResponseMapper {

    @Mappings({
        @Mapping(expression = "java(catalog.getCatalogIdentifier().getCatalogId())",target = "catalogId"),
    })
    CatalogResponseModel entityToResponseModel(Catalog catalog);

    List<CatalogResponseModel> entityListToResponseModelList(List<Catalog> catalogList);

//
//   @AfterMapping
//    default void addLinks(@MappingTarget CatalogResponseModel catalogResponseModel){
//        //Catalog
//        Link catalogLink  = linkTo(methodOn(CatalogController.class)
//                .getCatalogById(catalogResponseModel.getCatalogId()))
//                .withSelfRel();
//        catalogResponseModel.add(catalogLink);
//
//
//        //All catalogs
//        Link allCatalogs = linkTo(methodOn(CatalogController.class)
//                .getAllCatalogs(new HashMap<>()))
//                .withRel("allCatalogs");
//        catalogResponseModel.add(allCatalogs);
//
//
//    }

}
