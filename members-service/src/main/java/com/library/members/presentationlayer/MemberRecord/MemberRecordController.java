package com.library.members.presentationlayer.MemberRecord;


import com.library.members.Utils.exceptions.InvalidInputException;
import com.library.members.businesslayer.MemberRecord.MemberRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/memberRecords")
@RequiredArgsConstructor
public class MemberRecordController {


    @Autowired
    private final MemberRecordService memberRecordService;

    private static final int UUID_LENGTH = 36;



    @GetMapping
    public ResponseEntity<List<MemberRecordResponseModel>> getRecords(){

        return ResponseEntity.ok().body(memberRecordService.findRecords());
    }



    @GetMapping("/{memberId}")
    public ResponseEntity<MemberRecordResponseModel> getRecordById(@PathVariable String memberId){
        if (memberId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid memberId length: " + memberId);
        }
        return ResponseEntity.ok().body(memberRecordService.findRecordById(memberId));

    }



    @PostMapping()
    public ResponseEntity<MemberRecordResponseModel> newRecord(@RequestBody MemberRecordRequestModel memberRecordRequestModel){

        return new ResponseEntity<>(this.memberRecordService.newRecord(memberRecordRequestModel), HttpStatus.CREATED);
    }








    @PutMapping("/{memberId}")
    public ResponseEntity<MemberRecordResponseModel> updateRecord(@RequestBody MemberRecordRequestModel memberRecordRequestModel,
                                                        @PathVariable String memberId){
        if (memberId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid memberId length: " + memberId);
        }
        return ResponseEntity.ok().body(memberRecordService.updateRecord(memberRecordRequestModel,memberId));
    }



    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void>  deleteRecord(@PathVariable String memberId){
        if (memberId.length() != UUID_LENGTH) {
            throw new InvalidInputException( "Invalid memberId length: " + memberId);
        }
        this.memberRecordService.deleteRecord(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
