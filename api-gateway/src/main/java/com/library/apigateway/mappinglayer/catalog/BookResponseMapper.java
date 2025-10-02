package com.library.apigateway.mappinglayer.catalog;

import com.library.apigateway.presentationlayer.Catalog.BookController;
import com.library.apigateway.presentationlayer.Catalog.BookResponseModel;
import com.library.apigateway.presentationlayer.Catalog.CatalogController;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BookResponseMapper {

    BookResponseModel entityToResponseModel(BookResponseModel bookResponseModel);

    List<BookResponseModel> entityListToResponseModelList(List<BookResponseModel> bookResponseModel);



    @AfterMapping
    default  void addLinks(@MappingTarget BookResponseModel bookResponseModel){
        //Self link
        Link selfLink  = linkTo(methodOn(BookController.class)
                .getBookById(bookResponseModel.getCatalogId(),bookResponseModel.getBookId()))
                .withSelfRel();
        bookResponseModel.add(selfLink);


        //All other books
        Link allBookLink = linkTo(methodOn(BookController.class)
                .getAllBooks(bookResponseModel.getCatalogId()))
                .withRel("allBooks");
        bookResponseModel.add(allBookLink);



        //Catalog
        Link catalogLink  = linkTo(methodOn(CatalogController.class)
                .getCatalogById(bookResponseModel.getCatalogId()))
                .withSelfRel();
        bookResponseModel.add(catalogLink);


        //All catalogs
        Link allCatalogs = linkTo(methodOn(CatalogController.class)
                .getAllCatalogs())
                .withRel("allCatalogs");
        bookResponseModel.add(allCatalogs);


    }
}
