package com.library.catalogs.presentation;

import com.library.catalogs.datalayer.BookStatus;
import com.library.catalogs.datalayer.BookType;
import com.library.catalogs.datalayer.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseModel extends RepresentationModel<BookResponseModel> {

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
