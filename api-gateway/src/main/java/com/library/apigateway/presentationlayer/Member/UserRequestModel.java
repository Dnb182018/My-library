package com.library.apigateway.presentationlayer.Member;


import com.library.apigateway.domainclientlayer.members.UserPhoneNumber;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestModel {

    public String firstName;
    public String lastName;
    public String email;

    List<UserPhoneNumber> phoneNumberList;

    public String street_name;
    public String city;
    public String province;
    public String country;
    public String postal_code;

}
