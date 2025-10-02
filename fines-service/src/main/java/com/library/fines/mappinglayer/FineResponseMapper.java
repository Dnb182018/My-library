package com.library.fines.mappinglayer;

import com.library.fines.datalayer.Fine;
import com.library.fines.presentationlayer.FineController;
import com.library.fines.presentationlayer.FineResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface FineResponseMapper {
    @Mappings({
            @Mapping(expression = "java(fine.getFineIdentifier().getFineId())", target = "fineId"),
            @Mapping(expression = "java(fine.getIssueDate())", target = "issueDate"),
            @Mapping(expression =  "java(fine.getFinePaymentList())", target = "finePayment")
    })
    FineResponseModel entityToResponseModel(Fine fine);

    List<FineResponseModel> entityListToResponseModelList(List<Fine> fineList);


//    @AfterMapping
//    default  void addLinks(@MappingTarget FineResponseModel fineResponseModel){
//        //Self link
//        Link selfLink  = linkTo(methodOn(FineController.class)
//                .getFineByID(fineResponseModel.getFineId()))
//                .withSelfRel();
//        fineResponseModel.add(selfLink);
//
//
//        //All other fines
//        Link allFineLink = linkTo(methodOn(FineController.class)
//                .getAllFines())
//                .withRel("allFine");
//        fineResponseModel.add(allFineLink);
//
//
//
//
//
//
//    }



}
