package com.library.loans.mappinglayer;

import com.library.loans.dataacceslayer.Loan;
import com.library.loans.presentationlayer.LoanController;
import com.library.loans.presentationlayer.LoanResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface LoanResponseMapper {
    @Mapping(target = "loanId", source = "loan.loanIdentifier.loanId")


    @Mapping(expression = "java(loan.getCatalogModel().getBookId())", target = "bookId")
    @Mapping(expression = "java(loan.getCatalogModel().getTitle())", target = "title")


    @Mapping(expression = "java(loan.getMemberModel().getMemberId())", target = "memberId")
    @Mapping(expression = "java(loan.getMemberModel().getUserId())",target = "userId")
    @Mapping(expression = "java(loan.getMemberModel().getFirstName())",target = "firstName")
    @Mapping(expression = "java(loan.getMemberModel().getLastName())",target = "lastName")


    @Mapping(expression = "java(loan.getFineModel().getFineId())", target = "fineId")
    @Mapping(expression = "java(loan.getFineModel().getStatus())", target = "status")

    LoanResponseModel entityToResponseModel(Loan loan);
//
//    @AfterMapping
//    default void afterMapping(@MappingTarget LoanResponseModel loanResponseModel) {
//
//        //customer Link
//        Link selfLink = linkTo(methodOn(LoanController.class)
//                .getLoan(loanResponseModel.getLoanId(), loanResponseModel.getMemberId()))
//                .withSelfRel();
//
//        loanResponseModel.add(selfLink);
//
//        //all customers link
//        Link allLink = linkTo(methodOn(LoanController.class)
//                .getLoans(loanResponseModel.getMemberId()))
//                .withRel("all loans");
//
//        loanResponseModel.add(allLink);
//    }



}
