package com.library.catalogs.presentation;

import com.library.catalogs.Utils.exceptions.DuplicateBookException;
import com.library.catalogs.Utils.exceptions.InvalidInputException;
import com.library.catalogs.businesslayer.Books.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/catalogs")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private final BookService bookService;

    private static final int UUID_LENGTH = 36;

    @GetMapping("/{catalogId}/books")
    public ResponseEntity<List<BookResponseModel>> getAllBooks(@PathVariable String catalogId){

        if (catalogId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid catalogId length: " + catalogId);
        }

        return ResponseEntity.ok().body(bookService.findBooks(catalogId));
    }

    @GetMapping("/{catalogId}/books/{bookId}")
    public ResponseEntity<BookResponseModel> getBookById(@PathVariable String catalogId, @PathVariable String bookId){
        if (catalogId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid catalogId length: " + catalogId);
        }
        if (bookId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid bookID length: " + bookId);
        }
        return ResponseEntity.ok().body(bookService.findBookById(catalogId,bookId));

    }


    @PostMapping("/{catalogId}/books")
    public ResponseEntity<BookResponseModel> newBook(@RequestBody BookRequestModel bookRequestModel, @PathVariable String catalogId) {
        // Validate catalogId length
        if (catalogId.length() != UUID_LENGTH) {
            throw new InvalidInputException("Invalid catalogId length: " + catalogId);
        }

        // Check if the book already exists
        boolean exists = bookService.doesBookExist(bookRequestModel.getTitle(), catalogId, bookRequestModel.getAuthorId());

        if (exists) {
            // Throw DuplicateBookException if the book already exists
            throw new DuplicateBookException("Duplicate book found: " + bookRequestModel.getTitle() + " in catalog " + catalogId);
        }

        // If no duplicate, proceed with creating the new book
        BookResponseModel bookResponse = bookService.newBook(bookRequestModel, catalogId);

        // Return success response
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }





    @PutMapping("/{catalogId}/books/{bookId}")
    public ResponseEntity<BookResponseModel> updateBook(@RequestBody BookRequestModel bookRequestModel,@PathVariable String catalogId,@PathVariable String bookId){
        if (catalogId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid catalogId length: " + catalogId);
        }
        if (bookId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid bookID length: " + bookId);
        }
        return ResponseEntity.ok().body(bookService.updateBook(bookRequestModel,catalogId,bookId));
    }


    @DeleteMapping("/{catalogId}/books/{bookId}")
    public ResponseEntity<Void>  deletePurchase(@PathVariable String catalogId,@PathVariable String bookId){

        if (catalogId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid catalogId length: " + catalogId);
        }
        if (bookId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid bookID length: " + bookId);
        }

        this.bookService.deleteBook(catalogId,bookId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
