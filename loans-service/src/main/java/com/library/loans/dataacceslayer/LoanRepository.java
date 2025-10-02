package com.library.loans.dataacceslayer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoanRepository extends MongoRepository<Loan, String> {

    Loan findByMemberModel_MemberIdAndLoanIdentifier_LoanId(String memberId,String loanId);

    List<Loan> findAllByMemberModel_MemberId(String memberId);
}
