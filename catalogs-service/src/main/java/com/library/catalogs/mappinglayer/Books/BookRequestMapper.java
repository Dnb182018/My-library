package com.library.catalogs.mappinglayer.Books;

import com.library.catalogs.datalayer.Authors.AuthorIdentifier;
import com.library.catalogs.datalayer.Book;
import com.library.catalogs.datalayer.BookIdentifier;
import com.library.catalogs.datalayer.Catalog.CatalogIdentifier;
import com.library.catalogs.presentation.BookRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(expression =  "java(bookIdentifier)", target = "bookIdentifier"),
            @Mapping(expression =  "java(catalogIdentifier)", target = "catalogIdentifier"),
            @Mapping(expression =  "java(authorIdentifier)", target = "authorIdentifier"),



    })
    Book requestModelToentity(BookRequestModel bookRequestModel,
                              BookIdentifier bookIdentifier,
                              CatalogIdentifier catalogIdentifier,
                              AuthorIdentifier authorIdentifier);
}
