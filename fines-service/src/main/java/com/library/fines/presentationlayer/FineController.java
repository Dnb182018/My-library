package com.library.fines.presentationlayer;

import com.library.fines.Utils.exceptions.InvalidDateException;
import com.library.fines.Utils.exceptions.InvalidInputException;
import com.library.fines.businesslayer.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/fines")
@RequiredArgsConstructor
public class FineController {

    @Autowired
    private final FineService fineService;

    private static final int UUID_LENGTH = 36;



    @GetMapping
    public ResponseEntity<List<FineResponseModel>> getAllFines(){

        return ResponseEntity.ok().body(fineService.findFines());
    }



    @GetMapping("/{fineId}")
    public ResponseEntity<FineResponseModel> getFineByID(@PathVariable String fineId){
        if (fineId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid fineId length: " + fineId);
        }
        return ResponseEntity.ok().body(fineService.findFineByID(fineId));

    }



    @PostMapping()
    public ResponseEntity<FineResponseModel> newFine(@RequestBody FineRequestModel fineRequestModel){

        if (fineRequestModel.getIssueDate().isAfter(LocalDate.now())) {
            throw new InvalidDateException("Issue date cannot be in the future");
        }
        return new ResponseEntity<>(this.fineService.newFine(fineRequestModel), HttpStatus.CREATED);
    }



    @PutMapping("/{fineId}")
    public ResponseEntity<FineResponseModel> updateUser(@RequestBody FineRequestModel fineRequestModel,
                                                        @PathVariable String fineId){
        if (fineId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid fineId length: " + fineId);
        }

        if (fineRequestModel.getIssueDate().isAfter(LocalDate.now())) {
            throw new InvalidDateException("Issue date cannot be in the future");
        }
        return ResponseEntity.ok().body(fineService.updateFine(fineRequestModel,fineId));
    }



    @DeleteMapping("/{fineId}")
    public ResponseEntity<Void>  deleteFine(@PathVariable String fineId){

        if (fineId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid fineId length: " + fineId);
        }
        this.fineService.deleteFine(fineId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
