package com.library.members.datalayer.Users;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private UserIdentifier userIdentifier;

    private String firstName;

    private String lastName;

    private String email;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_phonenumbers", joinColumns = @JoinColumn(name = "user_id"))
    private List<UserPhoneNumber> phoneNumberList;

    @Embedded
    private UserAddress userAddress;

    public User(){
        this.userIdentifier = new UserIdentifier();
    }

    public User(String firstName, String lastName, String email, List<UserPhoneNumber> phoneNumberList, UserAddress userAddress) {
        this.userIdentifier =  new UserIdentifier();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumberList = phoneNumberList;
        this.userAddress = userAddress;
    }
}
