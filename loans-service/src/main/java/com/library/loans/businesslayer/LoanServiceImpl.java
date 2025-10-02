package com.library.loans.businesslayer;


import com.library.loans.Utils.exceptions.NotFoundException;
import com.library.loans.dataacceslayer.Loan;
import com.library.loans.dataacceslayer.LoanIdentifier;
import com.library.loans.dataacceslayer.LoanRepository;
import com.library.loans.dataacceslayer.LoanStatus;
import com.library.loans.domainClientLayer.catalogs.BookStatus;
import com.library.loans.domainClientLayer.catalogs.CatalogModel;
import com.library.loans.domainClientLayer.catalogs.CatalogsServiceClient;
import com.library.loans.domainClientLayer.fines.FineModel;
import com.library.loans.domainClientLayer.fines.FineServiceClient;
import com.library.loans.domainClientLayer.members.MemberModel;
import com.library.loans.domainClientLayer.members.MembersServiceClient;
import com.library.loans.mappinglayer.LoanRequestMapper;
import com.library.loans.mappinglayer.LoanResponseMapper;
import com.library.loans.presentationlayer.LoanRequestModel;
import com.library.loans.presentationlayer.LoanResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;
    private final LoanResponseMapper loanResponseMapper;
    private final LoanRequestMapper loanRequestMapper;
    private final MembersServiceClient membersServiceClient;
    private final CatalogsServiceClient catalogsServiceClient;
    private final FineServiceClient fineServiceClient;


    @Override
    public List<LoanResponseModel> getAllMemberLoans(String memberId) {

        // find the member using the member id
        MemberModel member = membersServiceClient.getMemberByMemberId(memberId);

        //check if the member exists
        if (member == null){
            throw new NotFoundException("This member doesn't exist" + memberId);
        }

        // return all the loans of the member
        return loanRepository.findAllByMemberModel_MemberId(memberId).stream()
                .map(loanResponseMapper::entityToResponseModel)
                .toList();
    }




    @Override
    public LoanResponseModel getMemberLoanById(String memberId, String loanId) {

        //find the loan using the loan id and the member id
        Loan loan = loanRepository.findByMemberModel_MemberIdAndLoanIdentifier_LoanId(memberId, loanId);

        if (loan == null) {
            throw new NotFoundException("Loan not found for memberId: " + memberId + " and loanId: " + loanId);
        }

        //check if member using member id and check if its exist
        MemberModel foundMember = membersServiceClient.getMemberByMemberId(memberId);
        if (foundMember == null){
            throw new NotFoundException("Unknown memberId provided: " + memberId);
        }

        //check if book exist
        CatalogModel foundBook = catalogsServiceClient.getBooksById(loan.getCatalogModel().getCatalogId(),loan.getCatalogModel().getBookId());
        if (foundBook == null){
            throw new NotFoundException("Unknown bookId provided: " + loan.getCatalogModel().getBookId());
        }

        // return the loan response model
        return loanResponseMapper.entityToResponseModel(loan);    }

    @Override
    public LoanResponseModel newLoan(LoanRequestModel loanRequestModel, String memberId) {

        //check if member using member id and check if its exist
        MemberModel foundMember = membersServiceClient.getMemberByMemberId(memberId);
        if (foundMember == null){
            throw new NotFoundException("Unknown memberId provided: " + memberId);
        }


        //check if book exist
        CatalogModel foundBook = catalogsServiceClient.getBooksById(loanRequestModel.getCatalogId(),loanRequestModel.getBookId());
        if (foundBook == null){
            throw new NotFoundException("Unknown bookId provided: " + loanRequestModel.getBookId());
        }


        //check if fines exist
        FineModel foundFines = fineServiceClient.getFineByID(loanRequestModel.getFineId());
        if (foundFines == null){
            throw new NotFoundException("Unknown fineId provided: " + loanRequestModel.getFineId());
        }

        Loan loan =  loanRequestMapper.requestModelToEntity(
                loanRequestModel,
                new LoanIdentifier(),
                foundMember,
                foundBook,
                foundFines
        );




        return loanResponseMapper.entityToResponseModel(loanRepository.save(loan));
    }

    @Override
    public LoanResponseModel updateLoan(LoanRequestModel loanRequestModel, String memberId, String loanId) {

        //find the loan using the loan id and the member id and check if the loan exists
        Loan loan = loanRepository.findByMemberModel_MemberIdAndLoanIdentifier_LoanId(memberId, loanId);
        if ( loan == null) {
            throw new NotFoundException("Unknown loanId: " + loanId);
        }

        //check if member using member id and check if its exist
        MemberModel foundMember = membersServiceClient.getMemberByMemberId(memberId);
        if (foundMember == null){
            throw new NotFoundException("Unknown memberId provided: " + memberId);
        }


        //check if book exist
        CatalogModel foundBook = catalogsServiceClient.getBooksById(loanRequestModel.getCatalogId(),loanRequestModel.getBookId());
        if (foundBook == null){
            throw new NotFoundException("Unknown bookId provided: " + loanRequestModel.getBookId());
        }


        //check if fines exist
        FineModel foundFines = fineServiceClient.getFineByID(loanRequestModel.getFineId());
        if (foundFines == null){
            throw new NotFoundException("Unknown fineId provided: " + loanRequestModel.getFineId());
        }

        //create the updated loan, set the id, and return the loan response model
        Loan updatedLoan =  loanRequestMapper.requestModelToEntity(
                loanRequestModel,
                loan.getLoanIdentifier(),
                foundMember,
                foundBook,
                foundFines
        );

        updatedLoan.setId(loan.getId());

        Loan savedLoan = loanRepository.save(updatedLoan);
//-----------------------------------------------------------------------------------------------
//
//        //TODO: aggreagate invariant
//        if(savedLoan.getLoanStatus() == LoanStatus.ACTIVES || savedLoan.getLoanStatus() == LoanStatus.OVERDUE){
//            CatalogModel updateBook = catalogsServiceClient.patchBookStatusByCatalogIDAndBookID(
//                    savedLoan.getCatalogModel().getCatalogId(),
//                    savedLoan.getCatalogModel().getBookId(),
//                    BookStatus.NOT_AVAILABLE.toString());
//
//            savedLoan.setCatalogModel(updateBook);
//            savedLoan = loanRepository.save(savedLoan);
//        }
//
//
//        if(savedLoan.getLoanStatus() == LoanStatus.RETURNED){
//            CatalogModel updateBook = catalogsServiceClient.patchBookStatusByCatalogIDAndBookID(
//                    savedLoan.getCatalogModel().getCatalogId(),
//                    savedLoan.getCatalogModel().getBookId(),
//                    BookStatus.AVAILABLE.toString());
//
//            savedLoan.setCatalogModel(updateBook);
//            savedLoan = loanRepository.save(savedLoan);
//        }
//
//        if(savedLoan.getLoanStatus() == LoanStatus.LOST){
//            CatalogModel updateBook = catalogsServiceClient.patchBookStatusByCatalogIDAndBookID(
//                    savedLoan.getCatalogModel().getCatalogId(),
//                    savedLoan.getCatalogModel().getBookId(),
//                    BookStatus.LOST.toString());
//
//            savedLoan.setCatalogModel(updateBook);
//            savedLoan = loanRepository.save(savedLoan);
//        }
//
//

        return loanResponseMapper.entityToResponseModel(savedLoan);
    }


    //-------------------------------------------------------------------------------------


    @Override
    public void deleteLoan(String memberId, String loanId) {

        Loan loan = loanRepository.findByMemberModel_MemberIdAndLoanIdentifier_LoanId(memberId, loanId);

        //check if member using member id and check if its exist
        MemberModel foundMember = membersServiceClient.getMemberByMemberId(memberId);
        if (foundMember == null){
            throw new NotFoundException("Unknown memberId provided: " + memberId);
        }

        if (loanId == null){
            throw new NotFoundException("Unknown loanId provided: " + loanId);
        }

        loanRepository.delete(loan);
    }
}
