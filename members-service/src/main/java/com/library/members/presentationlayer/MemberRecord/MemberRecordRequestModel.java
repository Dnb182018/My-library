package com.library.members.presentationlayer.MemberRecord;

import com.library.members.datalayer.MemberRecord.MemberType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRecordRequestModel {


    private String userId;
    private LocalDate start_date;
    private LocalDate end_date;
    private MemberType type;
}
