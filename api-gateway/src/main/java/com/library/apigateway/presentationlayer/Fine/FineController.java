package com.library.apigateway.presentationlayer.Fine;

import com.library.apigateway.businesslayer.Catalog.CatalogService;
import com.library.apigateway.businesslayer.Fines.FineService;
import com.library.apigateway.mappinglayer.fine.FineResponseMapper;
import com.library.apigateway.presentationlayer.Catalog.CatalogRequestModel;
import com.library.apigateway.presentationlayer.Catalog.CatalogResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/fines")
@RequiredArgsConstructor
@Slf4j
public class FineController {


    private final FineService fineService;



    @GetMapping(produces = "application/json")
    public ResponseEntity<List<FineResponseModel>> getAllFines(){

        return ResponseEntity.ok().body(fineService.findFines());
    }

    @GetMapping(value = "/{fineId}", produces = "application/json")
    public ResponseEntity<FineResponseModel> getFineBYID(@PathVariable String fineId){
        log.debug("1. Request Recieved in API-Gateway Fines Controller: ");
        return ResponseEntity.ok().body(fineService.findFineByID(fineId));

    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<FineResponseModel> createFines(@RequestBody FineRequestModel fineRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(fineService.newFine(fineRequestModel));
    }

    @PutMapping(value = "/{fineId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<FineResponseModel> updateFine(@RequestBody FineRequestModel fineRequestModel,@PathVariable String fineId ){
        return ResponseEntity.ok().body(fineService.updateFine(fineRequestModel,fineId));

    }

    @DeleteMapping(value = "/{fineId}")
    public ResponseEntity<FineResponseModel> deleteFine( @PathVariable String fineId){
        fineService.deleteFine(fineId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


}
