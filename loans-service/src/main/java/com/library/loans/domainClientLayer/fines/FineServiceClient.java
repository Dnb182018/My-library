package com.library.loans.domainClientLayer.fines;


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
public class FineServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String FINE_SERVICE_BASE_URL;

    public FineServiceClient(RestTemplate restTemplate,
                             ObjectMapper mapper,
                             @Value("fines-service") String finesServiceHost,
                             @Value("8080") String finesServicePort){
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        FINE_SERVICE_BASE_URL = "http://" + finesServiceHost + ":" + finesServicePort + "/api/v1/fines";
        System.out.println("Fines Service URL: " + FINE_SERVICE_BASE_URL);

    }



    //GET BY ID
    public FineModel getFineByID(String fineId) {
        try {
            String url = FINE_SERVICE_BASE_URL + "/" + fineId;
            log.debug("Calling Fines-Service with URL: {}", url);
            return restTemplate.getForObject(url, FineModel.class);
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

        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }

        log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
}
