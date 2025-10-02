package com.library.apigateway.businesslayer.Members;

import com.library.apigateway.presentationlayer.Member.MemberRecordRequestModel;
import com.library.apigateway.presentationlayer.Member.MemberRecordResponseModel;

import java.util.List;
import java.util.Map;

public interface MemberRecordService {

    //GET ALL
    List<MemberRecordResponseModel> findRecords();

    //GETONE
    MemberRecordResponseModel findRecordById (String memberId);

    //POST(NEW)
    MemberRecordResponseModel newRecord(MemberRecordRequestModel memberRecordRequestModel);

    //PUT (UPDATE)
    MemberRecordResponseModel updateRecord(MemberRecordRequestModel memberRecordRequestModel, String memberId);

    //DELETE
    void deleteRecord(String memberId);
}
