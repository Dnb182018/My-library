package com.library.apigateway.presentationlayer.Member;

import com.library.apigateway.businesslayer.Members.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    @Autowired
    private final UserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponseModel>> getAllUsers(){

        return ResponseEntity.ok().body(userService.findUsers());
    }



    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<UserResponseModel> getUserById(@PathVariable String userId){

        return ResponseEntity.ok().body(userService.findUserById(userId));

    }



    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponseModel> newUser(@RequestBody UserRequestModel userRequestModel){

        return new ResponseEntity<>(this.userService.newUser(userRequestModel), HttpStatus.CREATED);
    }








    @PutMapping(value ="/{userId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponseModel> updateUser(@RequestBody UserRequestModel userRequestModel,
                                                        @PathVariable String userId){

        return ResponseEntity.ok().body(userService.updateUSer(userRequestModel,userId));
    }



    @DeleteMapping(value ="/{userId}")
    public ResponseEntity<Void>  deleteUSer(@PathVariable String userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


