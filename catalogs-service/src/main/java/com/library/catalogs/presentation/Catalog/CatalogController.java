package com.library.catalogs.presentation.Catalog;

import com.library.catalogs.Utils.exceptions.InvalidInputException;
import com.library.catalogs.businesslayer.Catalog.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    @Autowired
    private final CatalogService catalogService;

    private static final int UUID_LENGTH = 36;



    @GetMapping
    public ResponseEntity<List<CatalogResponseModel>> getAllCatalogs(){

        return ResponseEntity.ok().body(catalogService.findCatalogs());
    }

    @GetMapping("/{catalogId}")
    public ResponseEntity<CatalogResponseModel> getCatalogById(@PathVariable String catalogId){
        if (catalogId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid catalogId length: " + catalogId);
        }
        return ResponseEntity.ok().body(catalogService.findCatalogById(catalogId));

    }



    @PostMapping()
    public ResponseEntity<CatalogResponseModel> newCatalog(@RequestBody CatalogRequestModel catalogRequestModel){

        return new ResponseEntity<>(this.catalogService.newCatalog(catalogRequestModel), HttpStatus.CREATED);
    }





    @PutMapping("/{catalogId}")
    public ResponseEntity<CatalogResponseModel> updateCatalog(@RequestBody CatalogRequestModel catalogRequestModel,
                                                              @PathVariable String catalogId){
        if (catalogId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid catalogId length: " + catalogId);
        }
        return ResponseEntity.ok().body(catalogService.updateCatalog(catalogRequestModel,catalogId));
    }



    @DeleteMapping("/{catalogId}")
    public ResponseEntity<Void>  deletePurchase(@PathVariable String catalogId){
        if (catalogId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid catalogId length: " + catalogId);
        }
        this.catalogService.deleteCatalog(catalogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
