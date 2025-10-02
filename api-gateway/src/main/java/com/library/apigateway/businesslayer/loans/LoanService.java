package com.library.apigateway.businesslayer.loans;


import com.library.apigateway.presentationlayer.loans.LoanRequestModel;
import com.library.apigateway.presentationlayer.loans.LoanResponseModel;

import java.util.List;

public interface LoanService {

    List<LoanResponseModel> getAllMemberLoans(String memberId);

    LoanResponseModel getMemberLoanById(String memberId, String loanId);

    LoanResponseModel newLoan(LoanRequestModel loanRequestModel, String memberId);

    LoanResponseModel updateLoan(LoanRequestModel loanRequestModel, String memberId, String loanId);

    void deleteLoan(String memberId, String loanId);
}
