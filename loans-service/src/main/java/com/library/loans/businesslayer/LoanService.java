package com.library.loans.businesslayer;


import com.library.loans.presentationlayer.LoanRequestModel;
import com.library.loans.presentationlayer.LoanResponseModel;

import java.util.List;

public interface LoanService {

    List<LoanResponseModel> getAllMemberLoans(String memberId);

    LoanResponseModel getMemberLoanById(String memberId, String loanId);

    LoanResponseModel newLoan(LoanRequestModel loanRequestModel, String memberId);

    LoanResponseModel updateLoan(LoanRequestModel loanRequestModel, String memberId, String loanId);

    void deleteLoan(String memberId, String loanId);
}
