package com.library.members.datalayer.Users;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class UserIdentifier {
    private String userId;

    public UserIdentifier(){
        this.userId = UUID.randomUUID().toString();
        System.out.println("Generated UUID: " + this.userId);
    }

    public UserIdentifier(String userId) {
        this.userId = userId;
    }
}
