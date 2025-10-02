package com.library.members.businesslayer.MemberRecord;

import com.library.members.presentationlayer.MemberRecord.MemberRecordRequestModel;
import com.library.members.presentationlayer.MemberRecord.MemberRecordResponseModel;

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
