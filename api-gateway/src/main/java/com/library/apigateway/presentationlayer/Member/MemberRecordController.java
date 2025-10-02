package com.library.apigateway.presentationlayer.Member;


import com.library.apigateway.businesslayer.Members.MemberRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/memberRecords")
@RequiredArgsConstructor
@Slf4j
public class MemberRecordController {


    @Autowired
    private final MemberRecordService memberService;


    @GetMapping(produces = "application/json")
    public ResponseEntity<List<MemberRecordResponseModel>> getMembers() {
        return ResponseEntity.ok().body(memberService.findRecords());
    }

    @GetMapping(value = "/{memberId}", produces = "application/json")
    public ResponseEntity<MemberRecordResponseModel> getMemberByMemberId(@PathVariable String memberId) {
        return ResponseEntity.ok().body(memberService.findRecordById(memberId));
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<MemberRecordResponseModel> addMember(@RequestBody MemberRecordRequestModel memberRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.newRecord(memberRequestModel));
    }
    @PutMapping(value = "/{memberId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<MemberRecordResponseModel> updateMember(@RequestBody MemberRecordRequestModel memberRequestModel, @PathVariable String memberId) {
        return ResponseEntity.ok().body(memberService.updateRecord(memberRequestModel,memberId));

    }

    @DeleteMapping(value = "/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable String memberId) {
        memberService.deleteRecord(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
