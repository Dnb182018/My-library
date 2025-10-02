package com.library.members.datalayer.MemberRecord;

import com.library.members.datalayer.Users.UserIdentifier;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "memberrecords")
@Data
public class MemberRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private MemberRecordIdentifier memberRecordIdentifier;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Embedded
    private UserIdentifier userIdentifier;

    public MemberRecord(){
        this.memberRecordIdentifier = new MemberRecordIdentifier();
    }

    public MemberRecord( LocalDate startDate, LocalDate endDate, MemberType type, UserIdentifier userIdentifier) {
        this.memberRecordIdentifier = new MemberRecordIdentifier();
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.userIdentifier = userIdentifier;
    }
}
