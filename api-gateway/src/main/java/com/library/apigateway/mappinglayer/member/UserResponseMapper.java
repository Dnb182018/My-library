package com.library.apigateway.mappinglayer.member;

import com.library.apigateway.presentationlayer.Member.UserController;
import com.library.apigateway.presentationlayer.Member.UserResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserResponseMapper {
    UserResponseModel entityToResponseModel(UserResponseModel userResponseModel);

    List<UserResponseModel> entityListToResponseModelList(List<UserResponseModel> userResponseModels);



    @AfterMapping
    default  void addLinks(@MappingTarget UserResponseModel userResponseModel){
        //Self link
        Link selfLink  = linkTo(methodOn(UserController.class)
                .getUserById(userResponseModel.getUserId()))
                .withSelfRel();
        userResponseModel.add(selfLink);


        //All other users
        Link allUsers = linkTo(methodOn(UserController.class)
                .getAllUsers())
                .withRel("allUsers");
        userResponseModel.add(allUsers);



    }
}
