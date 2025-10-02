package com.library.members.businesslayer.Users;

import com.library.members.presentationlayer.Users.UserRequestModel;
import com.library.members.presentationlayer.Users.UserResponseModel;

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
