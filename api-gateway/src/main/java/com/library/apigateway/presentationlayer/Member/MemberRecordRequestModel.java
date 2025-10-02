package com.library.apigateway.presentationlayer.Member;

import com.library.apigateway.domainclientlayer.members.MemberType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRecordRequestModel {


    private String userId;
    private LocalDate start_date;
    private LocalDate end_date;
    private MemberType type;
}
