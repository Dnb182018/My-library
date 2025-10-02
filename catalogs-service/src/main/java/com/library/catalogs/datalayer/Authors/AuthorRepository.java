package com.library.catalogs.datalayer.Authors;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findAuthorByAuthorIdentifier_AuthorId(String authorId);


}
