package com.library.apigateway.domainclientlayer.catalogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.apigateway.presentationlayer.Catalog.BookRequestModel;
import com.library.apigateway.presentationlayer.Catalog.BookResponseModel;
import com.library.apigateway.presentationlayer.Catalog.CatalogRequestModel;
import com.library.apigateway.presentationlayer.Catalog.CatalogResponseModel;
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
                                 @Value("${app.catalogs-service.host}") String catalogsServiceHost,
                                 @Value("${app.catalogs-service.port}") String catalogsServicePort){
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        CATALOGS_SERVICE_BASE_URL = "http://" + catalogsServiceHost + ":" + catalogsServicePort + "/api/v1/catalogs";
        System.out.println("Catalogs Service URL: " + CATALOGS_SERVICE_BASE_URL);

    }

//----------------------------CATALOGS----------------------------------

    //Get all catalogs
    public List<CatalogResponseModel> getAllCatalogs() {
        try {
            String url = CATALOGS_SERVICE_BASE_URL;

            CatalogResponseModel[] catalogResponseModels = restTemplate.getForObject(url, CatalogResponseModel[].class);
            assert catalogResponseModels != null;
            return List.of(catalogResponseModels);


        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    //Get Catalog ByID
    public CatalogResponseModel getCatalogsById(String catalogId) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId  ;
            log.debug("Calling Catalogs-Service with URL: {}", url);
            return restTemplate.getForObject(url, CatalogResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }



    //Create a catalog
    public CatalogResponseModel createCatalog(CatalogRequestModel catalogRequestModel) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL  ;

            return restTemplate.postForObject(url, catalogRequestModel, CatalogResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //Update a catalog
    public CatalogResponseModel updateCatalog(String catalogId, CatalogRequestModel catalogRequestModel) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId ;

            HttpEntity<CatalogRequestModel> requestEntity = new HttpEntity<>(catalogRequestModel);
            ResponseEntity<CatalogResponseModel> response = restTemplate.exchange(
                    url, HttpMethod.PUT, requestEntity, CatalogResponseModel.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //Delete a catalog
    public void deleteCatalog(String catalogId) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId ;

            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

//----------------------------------BOOKS-----------------------------------

    //Get book ByID
    public BookResponseModel getBooksById(String catalogId,String bookId) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId + "/books/" + bookId;
            log.debug("Calling Catalogs-Service with URL: {}", url);
            return restTemplate.getForObject(url, BookResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    //Get all books
    public List<BookResponseModel> getAllbooks( String catalogId) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId + "/books" ;

            BookResponseModel[] bookResponseModels = restTemplate.getForObject(url, BookResponseModel[].class);
            assert bookResponseModels != null;
            return List.of(bookResponseModels);


        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    //Create a book
    public BookResponseModel createBook(BookRequestModel bookRequestModel,String catalogId) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId + "/books" ;

            return restTemplate.postForObject(url, bookRequestModel, BookResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    //Update a book
    public BookResponseModel updateBook(String catalogId,String bookId, BookRequestModel bookRequestModel) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId + "/books/" + bookId ;

            HttpEntity<BookRequestModel> requestEntity = new HttpEntity<>(bookRequestModel);
            ResponseEntity<BookResponseModel> response = restTemplate.exchange(
                    url, HttpMethod.PUT, requestEntity, BookResponseModel.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


    //Delete a book
    public void deleteBook(String catalogId,String bookId) {
        try {
            String url = CATALOGS_SERVICE_BASE_URL + "/" + catalogId + "/books/" + bookId;

            restTemplate.delete(url);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }


//---------------------------------------------------------------------------

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
