package com.library.catalogs.datalayer.Authors;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private AuthorIdentifier authorIdentifier;

    private String firstName;

    private String lastName;

    private LocalDate birth_date;

    private String info;

    public Author(){
        this.authorIdentifier = new AuthorIdentifier();
    }

    public Author(AuthorIdentifier authorIdentifier, String firstName, String lastName, LocalDate birthdate, String info) {
        this.authorIdentifier = authorIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth_date = birthdate;
        this.info = info;
    }
}
