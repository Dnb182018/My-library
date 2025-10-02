package com.library.catalogs.businesslayer.Books;

import com.library.catalogs.Utils.exceptions.DuplicateBookException;
import com.library.catalogs.Utils.exceptions.NoMoreBookAvailableException;
import com.library.catalogs.Utils.exceptions.NotFoundException;
import com.library.catalogs.datalayer.Authors.Author;
import com.library.catalogs.datalayer.Authors.AuthorRepository;
import com.library.catalogs.datalayer.Book;
import com.library.catalogs.datalayer.BookIdentifier;
import com.library.catalogs.datalayer.BookRepository;
import com.library.catalogs.datalayer.Catalog.Catalog;
import com.library.catalogs.datalayer.Catalog.CatalogRepository;
import com.library.catalogs.mappinglayer.Books.BookRequestMapper;
import com.library.catalogs.mappinglayer.Books.BookResponseMapper;
import com.library.catalogs.presentation.BookRequestModel;
import com.library.catalogs.presentation.BookResponseModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final CatalogRepository catalogRepository;
    private final AuthorRepository authorRepository;
    private final BookRequestMapper bookRequestMapper;
    private final BookResponseMapper bookResponseMapper;


    //GET ALL
    @Override
    public List<BookResponseModel> findBooks( String catalogId) {
        Catalog foundCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);

        if (foundCatalog == null){
            throw new NotFoundException("Unknown catalogId: " + catalogId);
        }

        List<Book> bookList = bookRepository.findAllByCatalogIdentifier_CatalogId(catalogId);

        List<BookResponseModel> bookResponseModelList = new ArrayList<>();

        bookList.forEach(book -> {
           Author author = authorRepository.findAuthorByAuthorIdentifier_AuthorId(book.getAuthorIdentifier().getAuthorId());

           BookResponseModel bookResponseModel = bookResponseMapper.entityToResponseModel(book,author);

           bookResponseModelList.add(bookResponseModel);
        });

        return bookResponseModelList;
    }






    //GET ONE
    @Override
    public BookResponseModel findBookById(String catalogId, String bookId) {
        Catalog foundCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);

        if (foundCatalog == null){
            throw new NotFoundException("Unknown catalogId: " + catalogId);
        }

        Book foundBook = bookRepository.findBookByCatalogIdentifier_CatalogIdAndBookIdentifier_BookId(catalogId, bookId);

        if (foundBook == null){
            throw new NotFoundException("Unknown bookId: " + bookId);
        }

        Author author = authorRepository.findAuthorByAuthorIdentifier_AuthorId(foundBook.getAuthorIdentifier().getAuthorId());

        return bookResponseMapper.entityToResponseModel(foundBook,author);
    }





    //POST
    @Override
    public BookResponseModel newBook(BookRequestModel bookRequestModel, String catalogId) {
        Catalog foundCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);

        if (foundCatalog == null){
            throw new NotFoundException("Unknown catalogId: " + catalogId);
        }


        // Check if the author exists
        Author author = authorRepository.findAuthorByAuthorIdentifier_AuthorId(bookRequestModel.getAuthorId());


        BookIdentifier bookIdentifier = new BookIdentifier();


        Book tobeSaved = bookRequestMapper.requestModelToentity(bookRequestModel,bookIdentifier,foundCatalog.getCatalogIdentifier(),author.getAuthorIdentifier());

        if (tobeSaved.getQuantities().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NoMoreBookAvailableException("No more copies of book '" + tobeSaved.getTitle() + "' are available.");
        }

        Book savedBook = bookRepository.save(tobeSaved);





        return bookResponseMapper.entityToResponseModel(savedBook,author);
    }




    public boolean doesBookExist(String title, String catalogId, String authorId) {
        // Find the catalog and author
        Catalog foundCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);
        Author author = authorRepository.findAuthorByAuthorIdentifier_AuthorId(authorId);

        // If catalog or author is not found, return false (book cannot exist)
        if (foundCatalog == null || author == null) {
            return false;
        }

        // Check if the book already exists (based on title, catalog, and author)
        return bookRepository.existsByTitleAndCatalogIdentifierAndAuthorIdentifier(
                title,
                foundCatalog.getCatalogIdentifier(),
                author.getAuthorIdentifier()
        );
    }








    //PUT
    @Override
    public BookResponseModel updateBook(BookRequestModel bookRequestModel, String catalogId, String bookId) {
        Catalog foundCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);

        if (foundCatalog == null){
            throw new NotFoundException("Unknown catalogId: " + catalogId);
        }

        Book foundBook = bookRepository.findBookByCatalogIdentifier_CatalogIdAndBookIdentifier_BookId(catalogId, bookId);

        if (foundBook == null){
            throw new NotFoundException("Unknown bookId: " + bookId);
        }


        Author author = authorRepository.findAuthorByAuthorIdentifier_AuthorId(bookRequestModel.getAuthorId());

        if (foundBook == null){
            throw new NotFoundException("Unknown authorID: " + bookRequestModel.getAuthorId());
        }

        Book tobeSaved = bookRequestMapper.requestModelToentity(bookRequestModel,foundBook.getBookIdentifier(),foundCatalog.getCatalogIdentifier(),author.getAuthorIdentifier());

        tobeSaved.setId(foundBook.getId());

        Book savedBook = bookRepository.save(tobeSaved);

        return bookResponseMapper.entityToResponseModel(savedBook,author);

    }






    //DELETE
    @Transactional
    public void deleteBook(String catalogId, String bookId) {
        Catalog foundCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);

        if (foundCatalog == null){
            throw new NotFoundException("Unknown catalogId: " + catalogId);
        }

        Book foundBook = bookRepository.findBookByCatalogIdentifier_CatalogIdAndBookIdentifier_BookId(catalogId, bookId);

        if (foundBook == null){
            throw new NotFoundException("Unknown bookId: " + bookId);
        }
        else{
            this.bookRepository.deleteBookByCatalogIdentifier_CatalogIdAndBookIdentifier_BookId(catalogId,bookId);
        }
    }





}
