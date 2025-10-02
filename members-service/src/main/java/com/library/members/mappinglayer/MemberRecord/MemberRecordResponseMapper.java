package com.library.members.mappinglayer.MemberRecord;

import com.library.members.datalayer.MemberRecord.MemberRecord;
import com.library.members.datalayer.Users.User;
import com.library.members.presentationlayer.MemberRecord.MemberRecordController;
import com.library.members.presentationlayer.MemberRecord.MemberRecordResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.HashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface MemberRecordResponseMapper {

    @Mappings({
            @Mapping(expression = "java(memberRecord.getUserIdentifier().getUserId())",target = "userId"),
            @Mapping(expression = "java(memberRecord.getMemberRecordIdentifier().getMemberId())",target = "memberId"),
            @Mapping(expression = "java(memberRecord.getStartDate())", target = "start_date"),
            @Mapping(expression = "java(memberRecord.getEndDate())", target = "end_date")



    })
    MemberRecordResponseModel entityToResponseModel(MemberRecord memberRecord, User user);


//    @AfterMapping
//    default  void addLinks(@MappingTarget MemberRecordResponseModel memberRecordResponseModel){
//        //Self link
//        Link selfLink  = linkTo(methodOn(MemberRecordController.class)
//                .getRecordById(memberRecordResponseModel.getMemberId()))
//                .withSelfRel();
//        memberRecordResponseModel.add(selfLink);
//
//
//        //All other memberRecords
//        Link allRecords = linkTo(methodOn(MemberRecordController.class)
//                .getRecords())
//                .withRel("allRecords");
//        memberRecordResponseModel.add(allRecords);
//
//
//
//
//    }

}
