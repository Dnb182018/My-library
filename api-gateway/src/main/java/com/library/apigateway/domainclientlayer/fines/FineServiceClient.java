package com.library.apigateway.domainclientlayer.fines;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.apigateway.presentationlayer.Catalog.CatalogRequestModel;
import com.library.apigateway.presentationlayer.Catalog.CatalogResponseModel;
import com.library.apigateway.presentationlayer.Fine.FineRequestModel;
import com.library.apigateway.presentationlayer.Fine.FineResponseModel;
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
public class FineServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String FINES_SERVICE_BASE_URL;

    public FineServiceClient(RestTemplate restTemplate,
                                 ObjectMapper mapper,
                                 @Value("${app.fines-service.host}") String finesServiceHost,
                                 @Value("${app.fines-service.port}") String finesServicePort){
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        FINES_SERVICE_BASE_URL = "http://" + finesServiceHost + ":" + finesServicePort + "/api/v1/fines";
        System.out.println("Fines Service URL: " + FINES_SERVICE_BASE_URL);

    }

//----------------------------CATALOGS----------------------------------

    //Get all
    public List<FineResponseModel> getAllFines() {
        try {
            String url = FINES_SERVICE_BASE_URL;

            FineResponseModel[] fineResponseModels = restTemplate.getForObject(url, FineResponseModel[].class);
            assert fineResponseModels != null;
            return List.of(fineResponseModels);


        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    //Get ByID
    public FineResponseModel getFineByID(String fineId) {
        try {
            String url = FINES_SERVICE_BASE_URL + "/" + fineId  ;
            log.debug("Calling Fines-Service with URL: {}", url);
            return restTemplate.getForObject(url, FineResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }



    //Create
    public FineResponseModel createFine(FineRequestModel fineRequestModel) {
        try {
            String url = FINES_SERVICE_BASE_URL  ;

            return restTemplate.postForObject(url, fineRequestModel, FineResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //Update
    public FineResponseModel updateFine(String fineId, FineRequestModel fineRequestModel) {
        try {
            String url = FINES_SERVICE_BASE_URL + "/" + fineId;
            log.debug("Calling Fines-Service to update fine with URL: {}", url);

            HttpEntity<FineRequestModel> requestEntity = new HttpEntity<>(fineRequestModel);
            ResponseEntity<FineResponseModel> response = restTemplate.exchange(
                    url, HttpMethod.PUT, requestEntity, FineResponseModel.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //Delete
    public void deleteFine(String fineId) {
        try {
            String url = FINES_SERVICE_BASE_URL + "/" + fineId ;

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
        //add an if-statement for your domain-specific exception here
        log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
}
