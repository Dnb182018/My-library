package com.library.apigateway.presentationlayer.Member;


import com.library.apigateway.domainclientlayer.members.MemberType;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter

public class MemberRecordResponseModel extends RepresentationModel<MemberRecordResponseModel> {

    private String memberId;

    private String userId;
    private String firstName;
    private String lastName;

    private LocalDate start_date;
    private LocalDate end_date;
    private MemberType type;

}
