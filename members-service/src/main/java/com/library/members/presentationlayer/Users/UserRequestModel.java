package com.library.members.presentationlayer.Users;

import com.library.members.datalayer.Users.UserPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
