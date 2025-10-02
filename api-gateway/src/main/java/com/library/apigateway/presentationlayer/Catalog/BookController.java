package com.library.apigateway.presentationlayer.Catalog;

import com.library.apigateway.businesslayer.Catalog.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/catalogs")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "/{catalogId}/books",produces = "application/json")
    public ResponseEntity<List<BookResponseModel>> getAllBooks(@PathVariable String catalogId) {

        return ResponseEntity.ok().body(bookService.findBooks(catalogId));
    }

    @GetMapping(value = "/{catalogId}/books/{bookId}", produces = "application/json")
    public ResponseEntity<BookResponseModel> getBookById(@PathVariable String catalogId, @PathVariable String bookId) {

        return ResponseEntity.ok().body(bookService.findBookById(catalogId, bookId));

    }

    @PostMapping(value = "/{catalogId}/books",produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookResponseModel> createBook(@RequestBody BookRequestModel bookRequestModel, @PathVariable String catalogId){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.newBook(bookRequestModel,catalogId));
    }

    @PutMapping(value = "/{catalogId}/books/{bookId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookResponseModel> updateBook(@RequestBody BookRequestModel bookRequestModel,@PathVariable String catalogId,
                                           @PathVariable String bookId){
        return ResponseEntity.ok().body(bookService.updateBook(bookRequestModel,catalogId,bookId));


    }

    @DeleteMapping(value = "/{catalogId}/books/{bookId}")
    public ResponseEntity<BookResponseModel> deleteCatalog( @PathVariable String catalogId, @PathVariable String bookId){
        bookService.deleteBook(catalogId,bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
