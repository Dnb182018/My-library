package com.library.loans.domainClientLayer.catalogs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Component
@Slf4j
public class CatalogsServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String CATALOGS_SERVICE_BASE_URL;

    public CatalogsServiceClient(RestTemplate restTemplate,
                                 ObjectMapper mapper,
                                 @Value("catalogs-service") String catalogsServiceHost,
                                 @Value("8080") String catalogsServicePort){
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        CATALOGS_SERVICE_BASE_URL = "http://" + catalogsServiceHost + ":" + catalogsServicePort + "/api/v1/catalogs";
        System.out.println("Catalogs Service URL: " + CATALOGS_SERVICE_BASE_URL);

    }


    //Get Catalog ByID
    public CatalogModel getCatalogsById(String catalogId) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId  ;
            log.debug("Calling Catalogs-Service with URL: {}", url);
            return restTemplate.getForObject(url, CatalogModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


//----------------------------------BOOKS-----------------------------------

    //Get book ByID
    public CatalogModel getBooksById(String catalogId,String bookId) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId + "/books/" + bookId;
            log.debug("Calling Catalogs-Service with URL: {}", url);
            return restTemplate.getForObject(url, CatalogModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


//---------------------------------------------------------------------------



    public CatalogModel patchBookStatusByCatalogIDAndBookID(String catalogId, String bookId, String newStatus) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId + "/books/" + bookId;

            log.debug("Calling Catalogs-Service with URL: {}", url);

            //with ACL
            String response = restTemplate.patchForObject(url, newStatus, String.class);
            //no ACL
            //VehicleModel vehicleModel = restTemplate.patchForObject(url, newStatus, VehicleModel.class);

            CatalogModel catalogModel = restTemplate.getForObject(url, CatalogModel.class);

            return vclBookModel(response);

        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);

        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private CatalogModel vclBookModel(String response) throws JsonProcessingException {
            final JsonNode node = mapper.readTree(response);

            // Extract values for catalog
            final String catalogId = node.get("catalogId").asText();

            // Extract values for book
            final String bookId = node.get("bookId").asText();
            final String title = node.get("title").asText();



        CatalogModel bookModel1 = new CatalogModel(catalogId,bookId,title);

        return bookModel1;
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
