package com.library.catalogs.datalayer;

import com.library.catalogs.datalayer.Authors.AuthorIdentifier;
import com.library.catalogs.datalayer.Catalog.CatalogIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByCatalogIdentifier_CatalogId(String catalogId);

    Book findBookByCatalogIdentifier_CatalogIdAndBookIdentifier_BookId(String catalogId, String bookId);

    void deleteBookByCatalogIdentifier_CatalogIdAndBookIdentifier_BookId(String catalogId, String bookId);

    Book findBookByBookIdentifier_BookId(String bookId);

    boolean existsByTitleAndCatalogIdentifierAndAuthorIdentifier(
            String title,
            CatalogIdentifier catalogIdentifier,
            AuthorIdentifier authorIdentifier);


}
