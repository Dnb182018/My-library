package com.library.apigateway.businesslayer.Members;

import com.library.apigateway.domainclientlayer.members.MembersServiceClient;
import com.library.apigateway.presentationlayer.Member.UserRequestModel;
import com.library.apigateway.presentationlayer.Member.UserResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MembersServiceClient membersServiceClient;
    @Override
    public List<UserResponseModel> findUsers() {
        return membersServiceClient.getAllUsers();
    }

    @Override
    public UserResponseModel findUserById(String userId) {
        return membersServiceClient.getUserById(userId);
    }

    @Override
    public UserResponseModel newUser(UserRequestModel userRequestModel) {
        return membersServiceClient.newUser(userRequestModel);
    }

    @Override
    public UserResponseModel updateUSer(UserRequestModel userRequestModel, String userId) {
        return membersServiceClient.updateUser(userId,userRequestModel);
    }

    @Override
    public void deleteUser(String userId) {
        membersServiceClient.deleteUser(userId);
    }
}
