package com.library.apigateway.mappinglayer.loan;

import com.library.apigateway.presentationlayer.loans.LoanController;
import com.library.apigateway.presentationlayer.loans.LoanResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface LoanResponseMapper {
    LoanResponseModel entityToResponseModel(LoanResponseModel loanResponseModel);

    List<LoanResponseModel> entityListToModelList(List<LoanResponseModel> loanResponseModels);

    @AfterMapping
    default void afterMapping(@MappingTarget LoanResponseModel loanResponseModel) {

        //customer Link
        Link selfLink = linkTo(methodOn(LoanController.class)
                .getLoan(loanResponseModel.getLoanId(), loanResponseModel.getMemberId()))
                .withSelfRel();

        loanResponseModel.add(selfLink);

        //all customers link
        Link allLink = linkTo(methodOn(LoanController.class)
                .getLoans(loanResponseModel.getMemberId()))
                .withRel("all loans");

        loanResponseModel.add(allLink);
    }



}
