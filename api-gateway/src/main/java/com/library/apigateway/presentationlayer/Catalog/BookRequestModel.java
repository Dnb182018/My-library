package com.library.apigateway.presentationlayer.Catalog;

import com.library.apigateway.domainclientlayer.catalogs.BookStatus;
import com.library.apigateway.domainclientlayer.catalogs.BookType;
import com.library.apigateway.domainclientlayer.catalogs.Language;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestModel {
    private String title;
    private BookType type;
    private BigDecimal quantities;
    private String isbn;
    private Language language;
    private BookStatus status;
    private String description;

    private String authorId;
}
