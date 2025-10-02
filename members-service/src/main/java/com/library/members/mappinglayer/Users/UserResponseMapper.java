package com.library.members.mappinglayer.Users;
import com.library.members.datalayer.Users.User;
import com.library.members.presentationlayer.Users.UserController;
import com.library.members.presentationlayer.Users.UserResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {
    @Mappings({
            @Mapping(expression = "java(user.getUserIdentifier().getUserId())",target = "userId"),
            @Mapping(expression = "java(user.getUserAddress().getStreet_name())",target = "street_name"),
            @Mapping(expression = "java(user.getUserAddress().getCity())",target = "city"),
            @Mapping(expression = "java(user.getUserAddress().getCountry())",target = "country"),
            @Mapping(expression = "java(user.getUserAddress().getProvince())",target = "province"),
            @Mapping(expression = "java(user.getUserAddress().getPostal_code())",target = "postal_code"),
            @Mapping(expression = "java(user.getPhoneNumberList())",target = "phoneNumberList"),

    })
    UserResponseModel entityToResponseModel(User user);

    List<UserResponseModel> entityListToResponseModelList(List<User> userList);



//    @AfterMapping
//    default  void addLinks(@MappingTarget UserResponseModel userResponseModel){
//        //Self link
//        Link selfLink  = linkTo(methodOn(UserController.class)
//                .getUserById(userResponseModel.getUserId()))
//                .withSelfRel();
//        userResponseModel.add(selfLink);
//
//
//        //All other users
//        Link allUsers = linkTo(methodOn(UserController.class)
//                .getAllUsers())
//                .withRel("allUsers");
//        userResponseModel.add(allUsers);
//
//
//
//    }
}
