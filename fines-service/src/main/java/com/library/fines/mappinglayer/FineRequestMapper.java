package com.library.fines.mappinglayer;
import com.library.fines.datalayer.Fine;
import com.library.fines.datalayer.FineIdentifier;
import com.library.fines.datalayer.FinePayment;
import com.library.fines.presentationlayer.FineRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FineRequestMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(expression = "java(fineIdentifier)", target = "fineIdentifier"),
    })
    Fine requestModelToEntity (FineRequestModel fineRequestModel, FineIdentifier fineIdentifier, List<FinePayment> finePaymentList);
}
