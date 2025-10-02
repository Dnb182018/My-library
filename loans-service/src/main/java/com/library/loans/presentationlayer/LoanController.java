package com.library.loans.presentationlayer;

import com.library.loans.Utils.exceptions.InvalidInputException;
import com.library.loans.Utils.exceptions.NotFoundException;
import com.library.loans.businesslayer.LoanService;
import com.library.loans.domainClientLayer.catalogs.CatalogModel;
import com.library.loans.domainClientLayer.catalogs.CatalogsServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/memberRecords/{memberId}/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    private static final int UUID_LENGTH = 36;



    @GetMapping(produces = "application/json")
    public ResponseEntity<List<LoanResponseModel>> getLoans(@PathVariable String memberId) {
        if (memberId.length() != UUID_LENGTH){
            throw new InvalidInputException("Invalid memberId provided: " + memberId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getAllMemberLoans(memberId));
    }

    @GetMapping(value = "/{loanId}", produces = "application/json")
    public ResponseEntity<LoanResponseModel> getLoan(@PathVariable String memberId, @PathVariable String loanId) {
        if (memberId.length() != UUID_LENGTH){
            throw new InvalidInputException("Invalid memberId provided: " + memberId);
        }
        if (loanId.length() != UUID_LENGTH){
            throw new InvalidInputException("Invalid loanID provided: " + loanId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(loanService.getMemberLoanById(memberId, loanId));
    }


    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<LoanResponseModel> makeLoan(@RequestBody LoanRequestModel loanRequestModel,
                                                                         @PathVariable String memberId){
        if (memberId.length() != UUID_LENGTH){
            throw new InvalidInputException("Invalid memberId provided: " + memberId);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.newLoan(loanRequestModel,memberId));
    }

    @PutMapping(value = "/{loanId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<LoanResponseModel> updateLoan(@RequestBody LoanRequestModel loanRequestModel, @PathVariable String memberId, @PathVariable String loanId) {
        if (memberId.length() != UUID_LENGTH){
            throw new InvalidInputException("Invalid memberId provided: " + memberId);
        }if (loanId.length() != UUID_LENGTH){
            throw new InvalidInputException("Invalid loanID provided: " + loanId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(loanService.updateLoan(loanRequestModel, memberId, loanId));
    }

//
//    @PatchMapping
//    public CatalogsServiceClient updateBookStatus(String catalogId, String bookI,String newStatus ){
//        Catalog
//        if (catalogId == null){
//            throw new NotFoundException("Unknown catalogId provided: " + catalogId);
//        }
//    }


    @DeleteMapping(value = "/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable String memberId, @PathVariable String loanId) {
        if (memberId.length() != UUID_LENGTH){
            throw new InvalidInputException("Invalid memberId provided: " + memberId);
        }if (loanId.length() != UUID_LENGTH){
            throw new InvalidInputException("Invalid loanID provided: " + loanId);
        }
        loanService.deleteLoan(memberId, loanId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
