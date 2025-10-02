package com.library.members.presentationlayer.Users;

import com.library.members.datalayer.Users.UserPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseModel extends RepresentationModel<UserResponseModel> {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;

    List<UserPhoneNumber> phoneNumberList;

    private String street_name;
    private String city;
    private String province;
    private String country;
    private String postal_code;



}
