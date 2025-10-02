package com.library.catalogs.mappinglayer.Books;

import com.library.catalogs.datalayer.Authors.Author;
import com.library.catalogs.datalayer.Book;
import com.library.catalogs.presentation.BookController;
import com.library.catalogs.presentation.BookResponseModel;
import com.library.catalogs.presentation.Catalog.CatalogController;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.HashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {
    @Mappings({
        @Mapping(expression = "java(book.getCatalogIdentifier().getCatalogId())",target = "catalogId"),
        @Mapping(expression = "java(book.getBookIdentifier().getBookId())",target = "bookId"),
        @Mapping(expression = "java(book.getAuthorIdentifier().getAuthorId())",target = "authorId"),


    })
    BookResponseModel entityToResponseModel(Book book, Author author);

//
//
//    @AfterMapping
//    default  void addLinks(@MappingTarget BookResponseModel bookResponseModel){
//        //Self link
//        Link selfLink  = linkTo(methodOn(BookController.class)
//                .getBookById(bookResponseModel.getCatalogId(),bookResponseModel.getBookId()))
//                .withSelfRel();
//        bookResponseModel.add(selfLink);
//
//
//        //All other books
//        Link allBookLink = linkTo(methodOn(BookController.class)
//                .getAllBooks(bookResponseModel.getCatalogId(),new HashMap<>()))
//                .withRel("allBooks");
//        bookResponseModel.add(allBookLink);
//
//
//
//        //Catalog
//        Link catalogLink  = linkTo(methodOn(CatalogController.class)
//                .getCatalogById(bookResponseModel.getCatalogId()))
//                .withSelfRel();
//        bookResponseModel.add(catalogLink);
//
//
//        //All catalogs
//        Link allCatalogs = linkTo(methodOn(CatalogController.class)
//                .getAllCatalogs(new HashMap<>()))
//                .withRel("allCatalogs");
//        bookResponseModel.add(allCatalogs);
//
//
//    }
}
