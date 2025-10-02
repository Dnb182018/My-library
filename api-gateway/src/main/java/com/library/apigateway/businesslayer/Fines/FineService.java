package com.library.apigateway.businesslayer.Fines;


import com.library.apigateway.presentationlayer.Fine.FineRequestModel;
import com.library.apigateway.presentationlayer.Fine.FineResponseModel;

import java.util.List;
import java.util.Map;

public interface FineService {

    //GET ALL
    List<FineResponseModel> findFines();

    //GETONE
    FineResponseModel findFineByID (String fineId);

    //POST(NEW)
    FineResponseModel newFine(FineRequestModel fineRequestModel);

    //PUT (UPDATE)
    FineResponseModel updateFine(FineRequestModel fineRequestModel, String fineId);

    //DELETE
    void deleteFine(String fineId);

//    Boolean updateFineStatus(String fineId, Status status);
}
