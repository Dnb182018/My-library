package com.library.members.businesslayer.Users;
import com.library.members.Utils.exceptions.InvalidInputException;
import com.library.members.Utils.exceptions.NotFoundException;
import com.library.members.datalayer.Users.*;
import com.library.members.mappinglayer.Users.UserRequestMapper;
import com.library.members.mappinglayer.Users.UserResponseMapper;
import com.library.members.presentationlayer.Users.UserRequestModel;
import com.library.members.presentationlayer.Users.UserResponseModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{

    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;



    //GET ALL
    @Override
    public List<UserResponseModel> findUsers() {
        List<User> userList = userRepository.findAll();

        return userResponseMapper.entityListToResponseModelList(userList);
    }



    //GET ONE
    @Override
    public UserResponseModel findUserById(String userId) {
        User user = userRepository.findUserByUserIdentifier_UserId(userId);

        if (user == null){
            throw new NotFoundException("Unknown userId: " + userId);
        }
        return userResponseMapper.entityToResponseModel(user);
    }




    //NEW
    @Override
    public UserResponseModel newUser(UserRequestModel userRequestModel) {

        UserIdentifier userIdentifier = new UserIdentifier();

        UserAddress userAddress = new UserAddress(
                userRequestModel.getStreet_name(),
                userRequestModel.getCity(),
                userRequestModel.getProvince(),
                userRequestModel.getCountry(),
                userRequestModel.getPostal_code()
        );

        List<UserPhoneNumber> userPhoneNumbers = userRequestModel.getPhoneNumberList();

        User tobeSaved = userRequestMapper.requestModelToEntity(userRequestModel,userIdentifier,userAddress,userPhoneNumbers);

        User savedUser = userRepository.save(tobeSaved);

        return userResponseMapper.entityToResponseModel(savedUser);
    }



    //PUT
    @Override
    public UserResponseModel updateUSer(UserRequestModel userRequestModel, String userId) {
        User foundUser = userRepository.findUserByUserIdentifier_UserId(userId);

        if (foundUser == null){
            throw new NotFoundException("Unknown userId: " + userId);
        }

        UserAddress userAddress = new UserAddress(
                userRequestModel.getStreet_name(),
                userRequestModel.getCity(),
                userRequestModel.getProvince(),
                userRequestModel.getCountry(),
                userRequestModel.getPostal_code()
        );

        List<UserPhoneNumber> userPhoneNumbers = userRequestModel.getPhoneNumberList();

        User tobeSaved = userRequestMapper.requestModelToEntity(userRequestModel,foundUser.getUserIdentifier(),userAddress,userPhoneNumbers);

        tobeSaved.setId(foundUser.getId());

        User savedUser = userRepository.save(tobeSaved);

        return userResponseMapper.entityToResponseModel(savedUser);
    }




    //DELETE
    @Transactional
    public void deleteUser(String userId) {
        User foundUser = userRepository.findUserByUserIdentifier_UserId(userId);

        if (foundUser == null){
            throw new NotFoundException("Unknown userId: " + userId);
        }
        else{
            this.userRepository.deleteUserByUserIdentifier_UserId(userId);
        }
    }
}
