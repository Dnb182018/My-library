package com.library.apigateway.businesslayer.Members;

import com.library.apigateway.domainclientlayer.members.MembersServiceClient;
import com.library.apigateway.mappinglayer.member.MemberRecordResponseMapper;
import com.library.apigateway.presentationlayer.Member.MemberRecordRequestModel;
import com.library.apigateway.presentationlayer.Member.MemberRecordResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberRecordServiceImpl implements MemberRecordService{

    private final MembersServiceClient memberServiceClient;

    private final MemberRecordResponseMapper mapper;


    @Override
    public List<MemberRecordResponseModel> findRecords() {
        return mapper.entityLsitToResponseModelList(memberServiceClient.getAllMembers());    }

    @Override
    public MemberRecordResponseModel findRecordById(String memberId) {
        return mapper.entityToResponseModel(memberServiceClient.getMemberByMemberId(memberId));
    }

    @Override
    public MemberRecordResponseModel newRecord(MemberRecordRequestModel memberRecordRequestModel) {
        return mapper.entityToResponseModel(memberServiceClient.createMember(memberRecordRequestModel));
    }

    @Override
    public MemberRecordResponseModel updateRecord(MemberRecordRequestModel memberRecordRequestModel, String memberId) {
       return mapper.entityToResponseModel(memberServiceClient.updateMember(memberId, memberRecordRequestModel));
    }

    @Override
    public void deleteRecord(String memberId) {
        memberServiceClient.deleteMember(memberId);

    }
}
