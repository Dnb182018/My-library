package com.library.apigateway.mappinglayer.member;
import com.library.apigateway.businesslayer.Members.MemberRecordService;
import com.library.apigateway.presentationlayer.Member.MemberRecordController;
import com.library.apigateway.presentationlayer.Member.MemberRecordResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface MemberRecordResponseMapper {

    MemberRecordResponseModel entityToResponseModel(MemberRecordResponseModel memberRecordResponseModel);

    List<MemberRecordResponseModel> entityLsitToResponseModelList(List<MemberRecordResponseModel> recordResponseModels);


    @AfterMapping
    default  void addLinks(@MappingTarget MemberRecordResponseModel memberRecordResponseModel){
        //Self link
        Link selfLink  = linkTo(methodOn(MemberRecordController.class)
                .getMemberByMemberId(memberRecordResponseModel.getMemberId()))
                .withSelfRel();
        memberRecordResponseModel.add(selfLink);


        //All other memberRecords
        Link allRecords = linkTo(methodOn(MemberRecordController.class)
                .getMembers())
                .withRel("allRecords");
        memberRecordResponseModel.add(allRecords);




    }

}
