package com.library.members.businesslayer.MemberRecord;

import com.library.members.Utils.exceptions.InvalidInputException;
import com.library.members.Utils.exceptions.NotFoundException;
import com.library.members.datalayer.MemberRecord.MemberRecord;
import com.library.members.datalayer.MemberRecord.MemberRecordIdentifier;
import com.library.members.datalayer.MemberRecord.MemberRecordRepository;
import com.library.members.datalayer.Users.User;
import com.library.members.datalayer.Users.UserIdentifier;
import com.library.members.datalayer.Users.UserRepository;
import com.library.members.mappinglayer.MemberRecord.MemberRecordRequestMapper;
import com.library.members.mappinglayer.MemberRecord.MemberRecordResponseMapper;
import com.library.members.presentationlayer.MemberRecord.MemberRecordRequestModel;
import com.library.members.presentationlayer.MemberRecord.MemberRecordResponseModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberRecordServiceImpl implements MemberRecordService{

    private final MemberRecordRepository memberRecordRepository;
    private final MemberRecordResponseMapper memberRecordResponseMapper;
    private final MemberRecordRequestMapper memberRecordRequestMapper;
    private final UserRepository userRepository;


    //GET ALL
    @Override
    public List<MemberRecordResponseModel> findRecords() {
        List<MemberRecord> recordList = memberRecordRepository.findAll();

        List<MemberRecordResponseModel> recordResponseModelList = new ArrayList<>();

        recordList.forEach(record ->{
            User user = userRepository.findUserByUserIdentifier_UserId(record.getUserIdentifier().getUserId());

            MemberRecordResponseModel memberRecordResponseModel = memberRecordResponseMapper.entityToResponseModel(record,user);
            recordResponseModelList.add(memberRecordResponseModel);
        });

        return recordResponseModelList;
    }




    //GET ONE
    @Override
    public MemberRecordResponseModel findRecordById(String memberId) {
        MemberRecord record = memberRecordRepository.findMemberRecordByMemberRecordIdentifier_MemberId(memberId);

        if (record == null){
            throw new NotFoundException("Unknown memberId: " + memberId);
        }

        User user = userRepository.findUserByUserIdentifier_UserId(record.getUserIdentifier().getUserId());

        return memberRecordResponseMapper.entityToResponseModel(record,user);
    }





    //NEW
    @Override
    public MemberRecordResponseModel newRecord(MemberRecordRequestModel memberRecordRequestModel) {

        MemberRecordIdentifier memberRecordIdentifier = new MemberRecordIdentifier();

        UserIdentifier userIdentifier = new UserIdentifier(memberRecordRequestModel.getUserId());


        MemberRecord tobeSaved = memberRecordRequestMapper.requestModelToEntity(memberRecordRequestModel,memberRecordIdentifier,userIdentifier);

        tobeSaved.setStartDate(memberRecordRequestModel.getStart_date());
        tobeSaved.setEndDate(memberRecordRequestModel.getEnd_date());

        MemberRecord savedRecord = memberRecordRepository.save(tobeSaved);

        User user = userRepository.findUserByUserIdentifier_UserId(savedRecord.getUserIdentifier().getUserId());

        return memberRecordResponseMapper.entityToResponseModel(savedRecord,user);
    }





    //UPDATE
    @Override
    public MemberRecordResponseModel updateRecord(MemberRecordRequestModel memberRecordRequestModel, String memberId) {
        MemberRecord record = memberRecordRepository.findMemberRecordByMemberRecordIdentifier_MemberId(memberId);

        if (record == null){
            throw new NotFoundException("Unknown memberId: " + memberId);
        }

        UserIdentifier userIdentifier = new UserIdentifier(memberRecordRequestModel.getUserId());

        MemberRecord tobeSaved = memberRecordRequestMapper.requestModelToEntity(memberRecordRequestModel,record.getMemberRecordIdentifier(),userIdentifier);

        tobeSaved.setId(record.getId());
        tobeSaved.setStartDate(memberRecordRequestModel.getStart_date());
        tobeSaved.setEndDate(memberRecordRequestModel.getEnd_date());

        MemberRecord savedRecord = memberRecordRepository.save(tobeSaved);

        User user = userRepository.findUserByUserIdentifier_UserId(record.getUserIdentifier().getUserId());

        return memberRecordResponseMapper.entityToResponseModel(savedRecord,user);
    }





    //DELETE
    @Transactional
    public void deleteRecord(String memberId) {
        MemberRecord record = memberRecordRepository.findMemberRecordByMemberRecordIdentifier_MemberId(memberId);

        if (record == null){
            throw new NotFoundException("Unknown memberId: " + memberId);
        }
        else{
            this.memberRecordRepository.deleteMemberRecordByMemberRecordIdentifier_MemberId(memberId);
        }
    }
}
