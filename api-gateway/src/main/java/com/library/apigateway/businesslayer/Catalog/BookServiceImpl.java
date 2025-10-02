package com.library.apigateway.businesslayer.Catalog;

import com.library.apigateway.domainclientlayer.catalogs.CatalogsServiceClient;
import com.library.apigateway.mappinglayer.catalog.BookResponseMapper;
import com.library.apigateway.presentationlayer.Catalog.BookRequestModel;
import com.library.apigateway.presentationlayer.Catalog.BookResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final CatalogsServiceClient catalogsServiceClient;

    private final BookResponseMapper bookResponseMapper;

    @Override
    public List<BookResponseModel> findBooks(String catalogId) {
        return bookResponseMapper.entityListToResponseModelList(catalogsServiceClient.getAllbooks(catalogId));
    }

    @Override
    public BookResponseModel findBookById(String catalogId, String bookId) {
        return bookResponseMapper.entityToResponseModel(catalogsServiceClient.getBooksById(catalogId,bookId));
    }

    @Override
    public BookResponseModel newBook(BookRequestModel bookRequestModel, String catalogId) {
        return bookResponseMapper.entityToResponseModel(catalogsServiceClient.createBook(bookRequestModel,catalogId));
    }

    @Override
    public BookResponseModel updateBook(BookRequestModel bookRequestModel, String catalogId, String bookId) {
        return bookResponseMapper.entityToResponseModel(catalogsServiceClient.updateBook(catalogId,bookId, bookRequestModel));
    }

    @Override
    public void deleteBook(String catalogId, String bookId) {
        catalogsServiceClient.deleteBook(catalogId,bookId);
    }


//
//    //Aggregate Invariant
//    @Override
//    public Boolean updateBookStatus( String bookId, BookStatus status) {
//        Book foundBook = bookRepository.findBookByBookIdentifier_BookId( bookId);
//
//        if (foundBook == null){
//            throw new InvalidInputException("Unknown bookId: " + bookId);
//        }
//
//        foundBook.setStatus(status);
//
//        Book savedBook = bookRepository.save(foundBook);
//
//        if (savedBook != null){
//            return true;
//        }
//        return false;
//    }


}
