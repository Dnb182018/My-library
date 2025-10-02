package com.library.members.presentationlayer.MemberRecord;


import com.library.members.datalayer.MemberRecord.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRecordResponseModel extends RepresentationModel<MemberRecordResponseModel> {

    private String memberId;

    private String userId;
    private String firstName;
    private String lastName;

    private LocalDate start_date;
    private LocalDate end_date;
    private MemberType type;

}
