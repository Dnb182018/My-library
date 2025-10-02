package com.library.apigateway.businesslayer.loans;

import com.library.apigateway.domainclientlayer.loans.LoansServiceClient;
import com.library.apigateway.mappinglayer.loan.LoanResponseMapper;
import com.library.apigateway.presentationlayer.loans.LoanRequestModel;
import com.library.apigateway.presentationlayer.loans.LoanResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {
        private final LoansServiceClient loanServiceClient;

        private final LoanResponseMapper loanResponseMapper;


        @Override
        public List<LoanResponseModel> getAllMemberLoans(String memberId) {
                return loanResponseMapper.entityListToModelList(loanServiceClient.getAllLoans(memberId));
        }

        @Override
        public LoanResponseModel getMemberLoanById(String memberId, String loanId) {
                return loanResponseMapper.entityToResponseModel( loanServiceClient.getLoan(memberId, loanId));
        }

        @Override
        public LoanResponseModel newLoan(LoanRequestModel loanRequestModel, String memberId) {
                return loanResponseMapper.entityToResponseModel(loanServiceClient.createLoan(loanRequestModel, memberId));
        }

        @Override
        public LoanResponseModel updateLoan(LoanRequestModel loanRequestModel, String memberId, String loanId) {
                return loanResponseMapper.entityToResponseModel(loanServiceClient.updateLoan(loanRequestModel, memberId, loanId));
        }

        @Override
        public void deleteLoan(String memberId, String loanId) {
                loanServiceClient.deleteLoan(memberId, loanId);
        }
}
