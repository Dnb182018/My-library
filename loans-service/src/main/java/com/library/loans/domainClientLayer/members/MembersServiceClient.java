package com.library.loans.domainClientLayer.members;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.loans.Utils.HttpErrorInfo;
import com.library.loans.Utils.exceptions.InvalidInputException;
import com.library.loans.Utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@Slf4j
public class MembersServiceClient {

    private final RestTemplate restTemplate;

    private final ObjectMapper mapper;

    private final String MEMBER_SERVICE_BASE_URL;

    public MembersServiceClient(RestTemplate restTemplate, ObjectMapper mapper,
                                @Value("members-service") String membersServiceHost,
                                @Value("8080") String membersServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        MEMBER_SERVICE_BASE_URL = "http://" + membersServiceHost + ":" + membersServicePort + "/api/v1/memberRecords";
        System.out.println("Member Service URL: " + MEMBER_SERVICE_BASE_URL);
    }

//------------------------------MEMBER--------------------------------
    public MemberModel getMemberByMemberId(String memberId) {
        try{
            String url = MEMBER_SERVICE_BASE_URL + "/" + memberId;

            return restTemplate.getForObject(url, MemberModel.class);
        } catch (HttpClientErrorException ex) {
            throw  handleHttpClientException(ex);
        }
    }


    public MemberModel getUserById(String memberId, String userId) {
        try{
            String url = MEMBER_SERVICE_BASE_URL + "/" + memberId + "/users" + userId;

            return restTemplate.getForObject(url, MemberModel.class);
        } catch (HttpClientErrorException ex) {
            throw  handleHttpClientException(ex);
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
