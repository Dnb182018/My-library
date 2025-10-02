package com.library.catalogs.datalayer;

import com.library.catalogs.datalayer.Authors.AuthorIdentifier;
import com.library.catalogs.datalayer.Catalog.CatalogIdentifier;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private BookIdentifier bookIdentifier;

    @Embedded
    private CatalogIdentifier catalogIdentifier;

    @Embedded
    private AuthorIdentifier authorIdentifier;

    private String title;

    @Enumerated(EnumType.STRING)
    private BookType type;

    private BigDecimal quantities;

    private String isbn;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private String description;

    public Book(){
        this.bookIdentifier = new BookIdentifier();
    }

    public Book( CatalogIdentifier catalogIdentifier, AuthorIdentifier authorIdentifier, String title, BookType type, BigDecimal quantities, String isbn, Language language, BookStatus status, String description) {
        this.bookIdentifier = new BookIdentifier();
        this.catalogIdentifier = catalogIdentifier;
        this.authorIdentifier = authorIdentifier;
        this.title = title;
        this.type = type;
        this.quantities = quantities;
        this.isbn = isbn;
        this.language = language;
        this.status = status;
        this.description = description;
    }
}
