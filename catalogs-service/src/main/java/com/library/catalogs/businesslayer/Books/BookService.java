package com.library.catalogs.businesslayer.Books;

import com.library.catalogs.presentation.BookRequestModel;
import com.library.catalogs.presentation.BookResponseModel;

import java.util.List;
import java.util.Map;

public interface BookService {
    //GET ALL
    List<BookResponseModel> findBooks( String catalogId);

    //GETONE
    BookResponseModel findBookById (String catalogId, String bookId);

    //POST(NEW)
    BookResponseModel newBook(BookRequestModel bookRequestModel, String catalogId);

    //PUT (UPDATE)
    BookResponseModel updateBook(BookRequestModel bookRequestModel,String catalogId, String bookId);

    //DELETE
    void deleteBook(String catalogId, String bookId);

    boolean doesBookExist(String title, String catalogId, String authorId);


        //Aggregate Invariant
    //Boolean updateBookStatus( String bookId, BookStatus status);
}
