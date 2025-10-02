package com.library.apigateway.domainclientlayer.members;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.apigateway.presentationlayer.Fine.FineRequestModel;
import com.library.apigateway.presentationlayer.Fine.FineResponseModel;
import com.library.apigateway.presentationlayer.Member.MemberRecordRequestModel;
import com.library.apigateway.presentationlayer.Member.MemberRecordResponseModel;
import com.library.apigateway.presentationlayer.Member.UserRequestModel;
import com.library.apigateway.presentationlayer.Member.UserResponseModel;
import com.library.apigateway.utils.HttpErrorInfo;
import com.library.apigateway.utils.exceptions.InvalidInputException;
import com.library.apigateway.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@Slf4j
public class MembersServiceClient {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final String MEMBER_SERVICE_BASE_URL;

    private final String USER_SERVICE_BASE_URL;

    public MembersServiceClient(RestTemplate restTemplate, ObjectMapper mapper,
                                @Value("${app.members-service.host}") String memberServiceHost,
                                @Value("${app.members-service.port}") String memberServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        MEMBER_SERVICE_BASE_URL = "http://" + memberServiceHost + ":" + memberServicePort + "/api/v1/memberRecords";

        USER_SERVICE_BASE_URL = "http://" + memberServiceHost + ":" + memberServicePort + "/api/v1/users";

    }


    public MemberRecordResponseModel getMemberByMemberId(String memberId) {
        try{
            String url = MEMBER_SERVICE_BASE_URL + "/" + memberId;

            return restTemplate.getForObject(url, MemberRecordResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw  handleHttpClientException(ex);
        }
    }

    //get all members
    public List<MemberRecordResponseModel> getAllMembers() {
        try{
            String url = MEMBER_SERVICE_BASE_URL;

            MemberRecordResponseModel[] memberResponseModels = restTemplate.getForObject(url, MemberRecordResponseModel[].class);
            assert memberResponseModels != null;
            return List.of(memberResponseModels);

        } catch (HttpClientErrorException ex) {
            throw  handleHttpClientException(ex);
        }
    }

    //create a member
    public MemberRecordResponseModel createMember(MemberRecordRequestModel memberRequestModel) {
        try {
            String url = MEMBER_SERVICE_BASE_URL;

            return restTemplate.postForObject(url, memberRequestModel, MemberRecordResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //update a member
    public MemberRecordResponseModel updateMember(String memberId, MemberRecordRequestModel memberRequestModel) {
        try {
            String url = MEMBER_SERVICE_BASE_URL + "/" + memberId;
            HttpEntity<MemberRecordRequestModel> requestEntity = new HttpEntity<>(memberRequestModel);
            ResponseEntity<MemberRecordResponseModel> response = restTemplate.exchange(
                    url, HttpMethod.PUT, requestEntity, MemberRecordResponseModel.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //delete a member
    public void deleteMember(String memberId) {
        try {
            String url = MEMBER_SERVICE_BASE_URL + "/" + memberId;

            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //--------------------------User--------------------------------------


    public UserResponseModel getUserById(String userId) {
        try{
            String url = USER_SERVICE_BASE_URL + "/" + userId;

            return restTemplate.getForObject(url, UserResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw  handleHttpClientException(ex);
        }
    }

    //get all Users
    public List<UserResponseModel> getAllUsers() {
        try{
            String url = USER_SERVICE_BASE_URL;

            UserResponseModel[] userResponseModels = restTemplate.getForObject(url, UserResponseModel[].class);
            assert userResponseModels != null;
            return List.of(userResponseModels);

        } catch (HttpClientErrorException ex) {
            throw  handleHttpClientException(ex);
        }
    }

    //create a User
    public UserResponseModel newUser(UserRequestModel userRequestModel) {
        try {
            String url = USER_SERVICE_BASE_URL;

            return restTemplate.postForObject(url, userRequestModel, UserResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //update a User
    public UserResponseModel updateUser(String userId, UserRequestModel userRequestModel) {
        try {
            String url = USER_SERVICE_BASE_URL + "/" + userId;
            HttpEntity<UserRequestModel> requestEntity = new HttpEntity<>(userRequestModel);
            ResponseEntity<UserResponseModel> response = restTemplate.exchange(
                    url, HttpMethod.PUT, requestEntity, UserResponseModel.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //delete a User
    public void deleteUser(String userId) {
        try {
            String url = USER_SERVICE_BASE_URL + "/" + userId;

            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }









    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }












    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        //include all possible responses from the client
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
}
