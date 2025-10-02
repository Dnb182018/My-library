package com.library.apigateway.presentationlayer.Member;

import com.library.apigateway.domainclientlayer.members.UserPhoneNumber;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter

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
