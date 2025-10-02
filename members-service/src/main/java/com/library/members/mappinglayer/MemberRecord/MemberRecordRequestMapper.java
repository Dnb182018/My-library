package com.library.members.mappinglayer.MemberRecord;
import com.library.members.datalayer.MemberRecord.MemberRecord;
import com.library.members.datalayer.MemberRecord.MemberRecordIdentifier;
import com.library.members.datalayer.Users.UserIdentifier;
import com.library.members.presentationlayer.MemberRecord.MemberRecordRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MemberRecordRequestMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(expression =  "java(userIdentifier)", target = "userIdentifier"),
            @Mapping(expression =  "java(memberRecordIdentifier)", target = "memberRecordIdentifier"),


    })
    MemberRecord requestModelToEntity(MemberRecordRequestModel memberRecordRequestModel, MemberRecordIdentifier memberRecordIdentifier, UserIdentifier userIdentifier);
}
