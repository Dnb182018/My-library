package com.library.catalogs.presentation;

import com.library.catalogs.datalayer.*;
import com.library.catalogs.datalayer.Catalog.Catalog;
import com.library.catalogs.datalayer.Catalog.CatalogLocations;
import com.library.catalogs.datalayer.Catalog.CatalogRepository;
import com.library.catalogs.presentation.Catalog.CatalogRequestModel;
import com.library.catalogs.presentation.Catalog.CatalogResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


import javax.xml.stream.Location;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/data-h2.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerIntergrationTest {
    private final String FOUND_BOOK_ID = "550e8400-e29b-41d4-a716-446655440001";
    private final String NOT_FOUND_BOOK_ID = "550e8400-e29b-41d4-a716-446655440000";
    private final String BASE_URL_BOOKS = "api/v1/catalogs/{catalogId}/books";
    private final String INVALID_BOOK_ID = "invalid-id-123";
    private final String BASE_URL_CATALOGS = "api/v1/catalogs";
    private final String FOUND_CATALOG_ID = "3d43a4e6-f63c-4e8a-9798-bd4321a9280d";


    @Autowired
    private CatalogRepository catalogRepository;


    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private WebTestClient webTestClient;




    @Test
    public void whenGetCatalogs_thenReturnAllCatalogs() {
        //arrange
        long sizeDB = catalogRepository.count();

        //act and assert
        webTestClient.get()
                .uri(BASE_URL_CATALOGS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CatalogResponseModel.class)
                .value((list) -> {
                    assertNotNull(list);
                    assertNotEquals(0, list.size());
                    assertEquals(sizeDB, list.size());
                });
    }

    @Test
    public void whenCatalogExists_thenReturnCatalog() {
        //act
        webTestClient.get()
                .uri(BASE_URL_CATALOGS + "/" + FOUND_CATALOG_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CatalogResponseModel.class)
                .value(catalogResponseModel -> {
                    assertNotNull(catalogResponseModel);
                    assertEquals("VPL", catalogResponseModel.getLocation());
                });
    }


    @Test
    public void whenCreateCatalog_thenReturnNewCatalog() {
        // arrange
        CatalogRequestModel catalogRequestModel = buildSampleCatalogRequest();  // Method to build a sample catalog request model

        // act and assert
        webTestClient.post()
                .uri("api/v1/catalogs")  // No need for catalogId in URI since it's a new catalog
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(catalogRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CatalogRequestModel.class)
                .value(returnedCatalog -> {
                    assertNotNull(returnedCatalog);
                    assertEquals(catalogRequestModel.getLocation(), returnedCatalog.getLocation());
                });
    }

    @Test
    public void whenUpdateCatalog_thenReturnUpdatedCatalog() {
        // arrange
        List<Catalog> catalogs = catalogRepository.findAll();
        assertFalse(catalogs.isEmpty());

        Catalog catalog = catalogs.get(0); // Get the first catalog for updating
        String catalogId = catalog.getCatalogIdentifier().getCatalogId();
        CatalogRequestModel updatedCatalogRequest = buildSampleCatalogRequest();  // Method to build updated catalog data

        // act and assert
        webTestClient.put()
                .uri("api/v1/catalogs/{catalogId}", catalogId)  // Use catalogId to target the right catalog
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(updatedCatalogRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CatalogRequestModel.class)
                .value(returnedCatalog -> {
                    assertNotNull(returnedCatalog);
                    assertEquals(updatedCatalogRequest.getLocation(), returnedCatalog.getLocation());
                });
    }



    @Test
    void whenCatalogDeleted_thenReturnNoContent() {
        // Arrange
        List<Catalog> catalogs = catalogRepository.findAll();
        assertFalse(catalogs.isEmpty());

        Catalog catalog = catalogs.get(0);
        String catalogId = catalog.getCatalogIdentifier().getCatalogId();

        // Act & Assert
        webTestClient.delete()
                .uri("api/v1/catalogs/{catalogId}", catalogId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }










    @Test
    public void whenGetBooks_thenReturnAllBooks() {
        //arrange
        List<Catalog> catalogs = catalogRepository.findAll();
        assertFalse(catalogs.isEmpty());

        for (Catalog catalog : catalogs) {
            String catalogId = catalog.getCatalogIdentifier().getCatalogId();
            long expectedCount = bookRepository.findAll().stream()
                    .filter(book -> book.getCatalogIdentifier().getCatalogId().equals(catalogId))
                    .count();
            //act and assert
            webTestClient.get()
                    .uri("api/v1/catalogs/{catalogId}/books", catalogId)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBodyList(BookRequestModel.class)
                    .value(list -> {
                        assertNotNull(list);
                        assertEquals(expectedCount, list.size(),
                                "Mismatch for catalog ID: " + catalogId);
                    });
        }
    }

    @Test
    public void whenGetBookById_thenReturnBook() {
        // arrange
        List<Book> books = bookRepository.findAll();
        assertFalse(books.isEmpty());

        Book book = books.get(0);
        String catalogId = book.getCatalogIdentifier().getCatalogId();
        String bookId = book.getBookIdentifier().getBookId();

        // act & assert
        webTestClient.get()
                .uri("api/v1/catalogs/{catalogId}/books/{bookId}", catalogId, bookId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(BookRequestModel.class)
                .value(returnedBook -> {
                    assertNotNull(returnedBook);
                    assertEquals(book.getTitle(), returnedBook.getTitle());
                });
    }

    @Test
    public void whenCreateBook_thenReturnNewBook() {
        // arrange
        BookRequestModel bookRequestModel = buildSampleBookRequest();  // Method to build a sample book request model

        // act and assert
        webTestClient.post()
                .uri("api/v1/catalogs/{catalogId}/books",FOUND_CATALOG_ID)  // Replace with your actual catalogId
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(bookRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(BookRequestModel.class)
                .value(returnedBook -> {
                    assertNotNull(returnedBook);
                    assertEquals(bookRequestModel.getTitle(), returnedBook.getTitle());
                    assertEquals(bookRequestModel.getAuthorId(), returnedBook.getAuthorId());
                    assertEquals(bookRequestModel.getAuthorId(), returnedBook.getAuthorId());
                    assertEquals(bookRequestModel.getDescription(), returnedBook.getDescription());
                    assertEquals(bookRequestModel.getIsbn(), returnedBook.getIsbn());
                    assertEquals(bookRequestModel.getQuantities(), returnedBook.getQuantities());
                    assertEquals(bookRequestModel.getStatus(), returnedBook.getStatus());
                    assertEquals(bookRequestModel.getType(), returnedBook.getType());
                });
    }


    @Test
    public void whenUpdateBook_thenReturnUpdatedBook() {
        // arrange
        List<Book> books = bookRepository.findAll();
        assertFalse(books.isEmpty());

        Book book = books.get(0);
        String catalogId = book.getCatalogIdentifier().getCatalogId();
        String bookId = book.getBookIdentifier().getBookId();
        BookRequestModel updatedBookRequest = buildSampleBookRequest();  // Method to build updated book data

        // act and assert
        webTestClient.put()
                .uri("api/v1/catalogs/{catalogId}/books/{bookId}", catalogId, bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(updatedBookRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(BookRequestModel.class)
                .value(returnedBook -> {
                    assertNotNull(returnedBook);
                    assertEquals(updatedBookRequest.getTitle(), returnedBook.getTitle());
                    assertEquals(updatedBookRequest.getAuthorId(), returnedBook.getAuthorId());
                    assertEquals(updatedBookRequest.getAuthorId(), returnedBook.getAuthorId());
                    assertEquals(updatedBookRequest.getDescription(), returnedBook.getDescription());
                    assertEquals(updatedBookRequest.getIsbn(), returnedBook.getIsbn());
                    assertEquals(updatedBookRequest.getQuantities(), returnedBook.getQuantities());
                    assertEquals(updatedBookRequest.getStatus(), returnedBook.getStatus());
                    assertEquals(updatedBookRequest.getType(), returnedBook.getType());




                });
    }


    @Test
    void whenBookDeleted_thenReturnNoContent() {
        // Arrange
        List<Book> books = bookRepository.findAll();
        assertFalse(books.isEmpty());

        Book book = books.get(0);
        String catalogId = book.getCatalogIdentifier().getCatalogId();
        String bookId = book.getBookIdentifier().getBookId();

        // Act & Assert
        webTestClient.delete()
                .uri("api/v1/catalogs/{catalogId}/books/{bookId}", catalogId, bookId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }




    //_____________________________NEGATIVE_________________________
    @Test
    public void whenGetBooksWithInvalidOrNotFoundCatalogId_thenReturnClientError() {
        // Attempt with NOT_FOUND_BOOK_ID (UUID format, but not present)
        webTestClient.get()
                .uri(BASE_URL_BOOKS, NOT_FOUND_BOOK_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

        // Attempt with INVALID_BOOK_ID (invalid format)
        webTestClient.get()
                .uri(BASE_URL_BOOKS, INVALID_BOOK_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(422); // or isEqualTo(400), based on your validation
    }

    @Test
    void whenNoBooksExist_thenReturnEmptyList() {
        bookRepository.deleteAll(); // Ensure database is empty

        webTestClient.get()
                .uri("api/v1/catalogs/{catalogId}/books", FOUND_CATALOG_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookResponseModel.class)
                .value(books -> assertTrue(books.isEmpty())); // Expect empty list
    }


    @Test
    void whenNoCatalogsExist_thenReturnEmptyList() {
        catalogRepository.deleteAll(); // Ensure database is empty

        webTestClient.get()
                .uri("api/v1/catalogs")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CatalogResponseModel.class)
                .value(catalogs -> assertTrue(catalogs.isEmpty())); // Expect empty list
    }


    @Test
    void whenBookQuantitiesZero_thenThrowNoMoreBookAvailableException() {
        // arrange: ensure a book exists with 0 quantities
        Book book = new Book();
        book.setQuantities(BigDecimal.ZERO);
        bookRepository.save(book);

        // act & assert
        webTestClient.post()
                .uri("api/v1/catalogs/{catalogId}/books/{bookId}", FOUND_CATALOG_ID, FOUND_BOOK_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(msg -> assertTrue(msg.contains("No more copies")));
    }









    private BookRequestModel buildSampleBookRequest() {
        return BookRequestModel.builder()
                .title("The Great Gatsby")
                .type(BookType.EBOOK)
                .quantities(BigDecimal.valueOf(22))
                .isbn("978-0-7432-7356-5")
                .language(Language.ENGLISH)
                .status(BookStatus.AVAILABLE)
                .description("A novel set in the Roaring Twenties.")
                .authorId("d25a93e2-7ff3-4a4b-9ed2-34f55c4ef0d5")  // Example author ID
                .build();
    }

    private CatalogRequestModel buildSampleCatalogRequest() {
        return CatalogRequestModel.builder()
                .location("LOC")
                .build();
    }





}



