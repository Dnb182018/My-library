package com.library.apigateway.presentationlayer.Catalog;

import com.library.apigateway.domainclientlayer.catalogs.BookStatus;
import com.library.apigateway.domainclientlayer.catalogs.BookType;
import com.library.apigateway.domainclientlayer.catalogs.Language;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BookResponseModel extends RepresentationModel<BookResponseModel>  {

    private String catalogId;
    private String bookId;
    private String title;
    private BookType type;
    private BigDecimal quantities;
    private String isbn;
    private Language language;
    private BookStatus status;
    private String description;

    private String authorId;
    private String firstName;
    private String lastName;

}
