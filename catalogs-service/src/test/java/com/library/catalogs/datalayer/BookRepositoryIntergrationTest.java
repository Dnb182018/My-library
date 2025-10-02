package com.library.catalogs.datalayer;

import com.library.catalogs.datalayer.Authors.AuthorIdentifier;
import com.library.catalogs.datalayer.Catalog.Catalog;
import com.library.catalogs.datalayer.Catalog.CatalogIdentifier;
import com.library.catalogs.datalayer.Catalog.CatalogLocations;
import com.library.catalogs.datalayer.Catalog.CatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
//@ActiveProfiles("h2")
class BookRepositoryIntergrationTest {
    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private CatalogRepository catalogRepository;

    @BeforeEach
    public void setupDb(){
        bookRepository.deleteAll();
        catalogRepository.deleteAll();
    }

    @Test
    public void whenBooksExist_thenReturnAllBooks() {
        // Arrange
        CatalogIdentifier catalogIdentifier = new CatalogIdentifier("c1234567-89ab-cdef-0123-456789abcdef");
        AuthorIdentifier authorIdentifier = new AuthorIdentifier("d2345678-90bc-def1-2345-67890abcdef1");

        Book book1 = new Book(
                catalogIdentifier,
                authorIdentifier,
                "1984",
                BookType.HARDCOVER,
                BigDecimal.valueOf(20.00),
                "978-0-452-28423-4",
                Language.ENGLISH,
                BookStatus.AVAILABLE,
                "A dystopian social science fiction novel and cautionary tale."
        );

        Book book2 = new Book(
                catalogIdentifier,
                authorIdentifier,
                "Brave New World",
                BookType.PAPERPACK,
                BigDecimal.valueOf(18.50),
                "978-0-06-085052-4",
                Language.ENGLISH,
                BookStatus.RESERVE,
                "A novel about a futuristic society driven by technological advancements and control."
        );

        bookRepository.save(book1);
        bookRepository.save(book2);

        long bookCount = bookRepository.count();

        // Act
        List<Book> bookList = bookRepository.findAll();

        // Assert
        assertNotNull(bookList);
        assertNotEquals(0, bookCount);
        assertEquals(bookCount, bookList.size());
    }

    @Test
    public void whenBookExists_ReturnBookByBookId() {
        //arrange
        CatalogIdentifier catalogIdentifier = new CatalogIdentifier("d846a5a7-2e1c-4c79-809c-4f3f471e826d");
        AuthorIdentifier authorIdentifier = new AuthorIdentifier("e62f23f3-7430-4293-bd43-78c66a4a72f5");

        Book book = new Book(
                catalogIdentifier,
                authorIdentifier,
                "Go Set a Watchman",
                BookType.HARDCOVER,
                BigDecimal.valueOf(15),
                "978-0-06-240985-0",
                Language.ENGLISH,
                BookStatus.AVAILABLE,
                "A controversial follow-up to To Kill a Mockingbird, exploring racial tensions and personal growth."
        );

        bookRepository.save(book);
        //act
        Book savedBook = bookRepository.findBookByBookIdentifier_BookId(book.getBookIdentifier().getBookId());

        //assert
        assertNotNull(savedBook);
        assertEquals(book.getBookIdentifier(), savedBook.getBookIdentifier());
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getCatalogIdentifier(), savedBook.getCatalogIdentifier());
    }

    @Test
    public void whenBookEntityIsValid_thenAddBook() {
        // Arrange
        CatalogIdentifier catalogIdentifier = new CatalogIdentifier("a1234567-89ab-cdef-0123-456789abcdef");
        AuthorIdentifier authorIdentifier = new AuthorIdentifier("b2345678-90bc-def1-2345-67890abcdef1");

        Book book = new Book(
                catalogIdentifier,
                authorIdentifier,
                "The Midnight Library",
                BookType.PAPERPACK,
                BigDecimal.valueOf(18.99),
                "978-1-250-26084-1",
                Language.ENGLISH,
                BookStatus.AVAILABLE,
                "A thought-provoking novel exploring infinite lives and choices."
        );

        // Act
        bookRepository.save(book);
        Book savedBook = bookRepository.findBookByBookIdentifier_BookId(book.getBookIdentifier().getBookId());

        // Assert
        assertNotNull(savedBook);
        assertNotNull(savedBook.getId());
        assertNotNull(savedBook.getBookIdentifier());
        assertEquals(book.getBookIdentifier(), savedBook.getBookIdentifier());
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getCatalogIdentifier(), savedBook.getCatalogIdentifier());
        assertEquals(book.getAuthorIdentifier(), savedBook.getAuthorIdentifier());
        assertEquals(book.getIsbn(), savedBook.getIsbn());
        assertEquals(book.getType(), savedBook.getType());
        assertEquals(book.getQuantities(), savedBook.getQuantities());
        assertEquals(book.getLanguage(), savedBook.getLanguage());
        assertEquals(book.getStatus(), savedBook.getStatus());
        assertEquals(book.getDescription(), savedBook.getDescription());
    }

    @Test
    public void whenBookExists_thenUpdateBook() {
        // Arrange
        CatalogIdentifier catalogIdentifier = new CatalogIdentifier("a1234567-89ab-cdef-0123-456789abcdef");
        AuthorIdentifier authorIdentifier = new AuthorIdentifier("b2345678-90bc-def1-2345-67890abcdef1");

        Book book = new Book(
                catalogIdentifier,
                authorIdentifier,
                "Original Title",
                BookType.HARDCOVER,
                BigDecimal.valueOf(20.00),
                "978-0-00-000000-0",
                Language.ENGLISH,
                BookStatus.AVAILABLE,
                "Original description"
        );

        bookRepository.save(book);
        Book existingBook = bookRepository.findBookByBookIdentifier_BookId(book.getBookIdentifier().getBookId());

        // Modify fields
        existingBook.setTitle("Updated Title");
        existingBook.setDescription("Updated description");
        existingBook.setStatus(BookStatus.LOST);

        // Act
        Book updatedBook = bookRepository.save(existingBook);

        // Assert
        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals("Updated description", updatedBook.getDescription());
        assertEquals(BookStatus.LOST, updatedBook.getStatus());
    }

    @Test
    public void whenBookExists_thenDeleteBook() {
        // Arrange
        CatalogIdentifier catalogIdentifier = new CatalogIdentifier("a1234567-89ab-cdef-0123-456789abcdef");
        AuthorIdentifier authorIdentifier = new AuthorIdentifier("b2345678-90bc-def1-2345-67890abcdef1");

        Book book = new Book(
                catalogIdentifier,
                authorIdentifier,
                "Delete Me",
                BookType.PAPERPACK,
                BigDecimal.valueOf(12.99),
                "978-1-111-11111-1",
                Language.ENGLISH,
                BookStatus.AVAILABLE,
                "This book will be deleted"
        );

        bookRepository.save(book);
        String bookId = book.getBookIdentifier().getBookId();

        // Act
        bookRepository.delete(book);
        Book deletedBook = bookRepository.findBookByBookIdentifier_BookId(bookId);

        // Assert
        assertNull(deletedBook);
    }



    @Test
    public void whenCatalogsExist_thenReturnAllCatalogs() {
        // Arrange
        Catalog catalog1 = new Catalog(CatalogLocations.SFPL);
        Catalog catalog2 = new Catalog(CatalogLocations.BPL);

        catalogRepository.save(catalog1);
        catalogRepository.save(catalog2);

        long catalogCount = catalogRepository.count();

        // Act
        List<Catalog> catalogList = catalogRepository.findAll();

        // Assert
        assertNotNull(catalogList);
        assertNotEquals(0, catalogCount);
        assertEquals(catalogCount, catalogList.size());
    }


    @Test
    public void whenCatalogExists_ReturnCatalogByCatalogId() {
        //arrange
        CatalogIdentifier catalogIdentifier = new CatalogIdentifier("d846a5a7-2e1c-4c79-809c-4f3f471e826d");

        Catalog catalog = new Catalog(CatalogLocations.SFPL);

        catalogRepository.save(catalog);
        //act
        Catalog savedCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalog.getCatalogIdentifier().getCatalogId());

        //assert
        assertNotNull(savedCatalog);
        assertEquals(catalog.getCatalogIdentifier(), savedCatalog.getCatalogIdentifier());
        assertEquals(catalog.getLocation(), savedCatalog.getLocation());
    }


    @Test
    public void whenCatalogEntityIsValid_thenAddCatalog() {
        // Arrange
        Catalog catalog = new Catalog(CatalogLocations.BPL);

        // Act
        catalogRepository.save(catalog);
        Catalog savedCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalog.getCatalogIdentifier().getCatalogId());

        // Assert
        assertNotNull(savedCatalog);
        assertNotNull(savedCatalog.getId());
        assertNotNull(savedCatalog.getCatalogIdentifier());
        assertEquals(catalog.getCatalogIdentifier(), savedCatalog.getCatalogIdentifier());
        assertEquals(catalog.getLocation(), savedCatalog.getLocation());
    }

    @Test
    public void whenCatalogExists_thenUpdateCatalog() {
        // Arrange
        Catalog catalog = new Catalog(CatalogLocations.SFPL);
        catalogRepository.save(catalog);
        Catalog existingCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalog.getCatalogIdentifier().getCatalogId());

        // Modify field
        existingCatalog.setLocation(CatalogLocations.LOC);

        // Act
        Catalog updatedCatalog = catalogRepository.save(existingCatalog);

        // Assert
        assertNotNull(updatedCatalog);
        assertEquals(CatalogLocations.LOC, updatedCatalog.getLocation());
    }

    @Test
    public void whenCatalogExists_thenDeleteCatalog() {
        // Arrange
        Catalog catalog = new Catalog(CatalogLocations.SFPL);
        catalogRepository.save(catalog);
        String catalogId = catalog.getCatalogIdentifier().getCatalogId();

        // Act
        catalogRepository.delete(catalog);
        Catalog deletedCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);

        // Assert
        assertNull(deletedCatalog);
    }





    //__________NEGATIVE___________
    @Test
    public void whenBookDoesNotExist_ReturnNull() {
        //act
        Book savedBook = bookRepository.findBookByBookIdentifier_BookId("1234");

        //assert
        assertNull(savedBook);
    }

    @Test
    public void whenCatalogDoesNotExist_ReturnNull() {
        //act
        Catalog savedCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId("1234");

        //assert
        assertNull(savedCatalog);
    }




}