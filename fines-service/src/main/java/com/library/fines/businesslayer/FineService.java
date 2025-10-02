package com.library.fines.businesslayer;

import com.library.fines.presentationlayer.FineRequestModel;
import com.library.fines.presentationlayer.FineResponseModel;

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
