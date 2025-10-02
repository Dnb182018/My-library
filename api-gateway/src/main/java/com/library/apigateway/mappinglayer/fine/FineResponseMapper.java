package com.library.apigateway.mappinglayer.fine;

import com.library.apigateway.presentationlayer.Fine.FineController;
import com.library.apigateway.presentationlayer.Fine.FineResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FineResponseMapper {
    FineResponseModel entityToResponseModel(FineResponseModel fineResponseModel);

    List<FineResponseModel> entityListToResponseModelList(List<FineResponseModel> fineResponseModels);


    @AfterMapping
    default  void addLinks(@MappingTarget FineResponseModel fineResponseModel){
        //Self link
        Link selfLink  = linkTo(methodOn(FineController.class)
                .getFineBYID(fineResponseModel.getFineId()))
                .withSelfRel();
        fineResponseModel.add(selfLink);


        //All other fines
        Link allFineLink = linkTo(methodOn(FineController.class)
                .getAllFines())
                .withRel("allFine");
        fineResponseModel.add(allFineLink);





        
    }



}
