package com.library.catalogs.datalayer.Authors;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;


@Embeddable
@Getter
public class AuthorIdentifier {

    private String authorId;

    public AuthorIdentifier(){
        this.authorId = UUID.randomUUID().toString();
        System.out.println("Generated UUID: " + this.authorId);
    }

    public AuthorIdentifier(String authorId){
        this.authorId = authorId;
    }
}
