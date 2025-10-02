package com.library.apigateway.presentationlayer.Catalog;


import com.library.apigateway.businesslayer.Catalog.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/catalogs")
@RequiredArgsConstructor
@Slf4j
public class CatalogController {

    private final CatalogService catalogService;


    @GetMapping(produces = "application/json")
    public ResponseEntity<List<CatalogResponseModel>> getAllCatalogs(){

        return ResponseEntity.ok().body(catalogService.findCatalogs());
    }

    @GetMapping(value = "/{catalogId}", produces = "application/json")
    public ResponseEntity<CatalogResponseModel> getCatalogById(@PathVariable String catalogId){
        log.debug("1. Request Recieved in API-Gateway Catalogs Controller: getCatalogsByID");
        return ResponseEntity.ok().body(catalogService.findCatalogById(catalogId));

    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogResponseModel> createCatalog(@RequestBody CatalogRequestModel catalogRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.newCatalog(catalogRequestModel));
    }

    @PutMapping(value = "/{catalogId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CatalogResponseModel> updateCatalog(@RequestBody CatalogRequestModel catalogRequestModel,@PathVariable String catalogId ){
        return ResponseEntity.ok().body(catalogService.updateCatalog(catalogRequestModel,catalogId));

    }

    @DeleteMapping(value = "/{catalogId}")
    public ResponseEntity<CatalogResponseModel> deleteCatalog( @PathVariable String catalogId){
        catalogService.deleteCatalog(catalogId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }




}
