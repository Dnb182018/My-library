package com.library.apigateway.businesslayer.Members;


import com.library.apigateway.presentationlayer.Member.UserRequestModel;
import com.library.apigateway.presentationlayer.Member.UserResponseModel;

import java.util.List;
import java.util.Map;

public interface UserService {

    //GET ALL
    List<UserResponseModel> findUsers();

    //GETONE
    UserResponseModel findUserById (String userId);

    //POST(NEW)
    UserResponseModel newUser(UserRequestModel userRequestModel);

    //PUT (UPDATE)
    UserResponseModel updateUSer(UserRequestModel userRequestModel, String userId);

    //DELETE
    void deleteUser(String userId);
}
