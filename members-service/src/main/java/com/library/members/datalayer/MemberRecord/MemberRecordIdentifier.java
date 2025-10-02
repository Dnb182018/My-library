package com.library.members.datalayer.MemberRecord;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class MemberRecordIdentifier {

    private String memberId;

    public MemberRecordIdentifier(){
        this.memberId = UUID.randomUUID().toString();
    }

    public MemberRecordIdentifier(String memberId){
        this.memberId  = memberId;
    }
}
