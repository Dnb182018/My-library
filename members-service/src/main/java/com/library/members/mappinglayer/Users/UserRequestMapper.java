package com.library.members.mappinglayer.Users;

import com.library.members.datalayer.Users.User;
import com.library.members.datalayer.Users.UserAddress;
import com.library.members.datalayer.Users.UserIdentifier;
import com.library.members.datalayer.Users.UserPhoneNumber;
import com.library.members.presentationlayer.Users.UserRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(expression =  "java(userIdentifier)", target = "userIdentifier"),
            @Mapping(expression =  "java(userAddress)", target = "userAddress"),
            @Mapping(expression =  "java(userPhoneNumber)", target = "phoneNumberList"),

    })
    User requestModelToEntity(UserRequestModel userRequestModel, UserIdentifier userIdentifier, UserAddress userAddress, List<UserPhoneNumber> userPhoneNumber);
}
