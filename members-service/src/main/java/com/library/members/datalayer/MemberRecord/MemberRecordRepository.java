package com.library.members.datalayer.MemberRecord;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRecordRepository extends JpaRepository<MemberRecord,Integer> {

    MemberRecord findMemberRecordByMemberRecordIdentifier_MemberId(String memberId);

    void deleteMemberRecordByMemberRecordIdentifier_MemberId(String memberId);
}
