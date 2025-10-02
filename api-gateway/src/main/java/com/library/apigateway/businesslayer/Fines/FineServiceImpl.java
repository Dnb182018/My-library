package com.library.apigateway.businesslayer.Fines;

import com.library.apigateway.domainclientlayer.catalogs.CatalogsServiceClient;
import com.library.apigateway.domainclientlayer.fines.FineServiceClient;
import com.library.apigateway.mappinglayer.fine.FineResponseMapper;
import com.library.apigateway.presentationlayer.Catalog.CatalogRequestModel;
import com.library.apigateway.presentationlayer.Catalog.CatalogResponseModel;
import com.library.apigateway.presentationlayer.Fine.FineRequestModel;
import com.library.apigateway.presentationlayer.Fine.FineResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {


    private final FineServiceClient fineServiceClient;
    private final FineResponseMapper fineResponseMapper;


    @Override
    public List<FineResponseModel> findFines() {
        return fineResponseMapper.entityListToResponseModelList(fineServiceClient.getAllFines());
    }

    @Override
    public FineResponseModel findFineByID(String fineId) {
        return fineResponseMapper.entityToResponseModel(fineServiceClient.getFineByID(fineId));
    }

    @Override
    public FineResponseModel newFine(FineRequestModel fineRequestModel) {
        return fineResponseMapper.entityToResponseModel(fineServiceClient.createFine(fineRequestModel));
    }

    @Override
    public FineResponseModel updateFine(FineRequestModel fineRequestModel, String fineId) {
        return fineResponseMapper.entityToResponseModel(fineServiceClient.updateFine(fineId, fineRequestModel));
    }

    @Override
    public void deleteFine(String fineId) {
        fineServiceClient.deleteFine(fineId);
    }
}
