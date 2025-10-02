package com.library.members.presentationlayer.Users;

import com.library.members.Utils.exceptions.InvalidInputException;
import com.library.members.businesslayer.Users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {


    @Autowired
    private final UserService userService;

    private static final int UUID_LENGTH = 36;

    @GetMapping
    public ResponseEntity<List<UserResponseModel>> getAllUsers(){

        return ResponseEntity.ok().body(userService.findUsers());
    }



    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseModel> getUserById(@PathVariable String userId){
        if (userId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid memberId length: " + userId);
        }
        return ResponseEntity.ok().body(userService.findUserById(userId));

    }



    @PostMapping()
    public ResponseEntity<UserResponseModel> newUser(@RequestBody UserRequestModel userRequestModel){

        return new ResponseEntity<>(this.userService.newUser(userRequestModel), HttpStatus.CREATED);
    }








    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseModel> updateUser(@RequestBody UserRequestModel userRequestModel,
                                                        @PathVariable String userId){
        if (userId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid memberId length: " + userId);
        }
        return ResponseEntity.ok().body(userService.updateUSer(userRequestModel,userId));
    }



    @DeleteMapping("/{userId}")
    public ResponseEntity<Void>  deleteUSer(@PathVariable String userId){
        if (userId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid memberId length: " + userId);
        }
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


