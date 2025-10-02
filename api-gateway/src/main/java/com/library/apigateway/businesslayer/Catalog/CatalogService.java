package com.library.apigateway.businesslayer.Catalog;


import com.library.apigateway.presentationlayer.Catalog.CatalogRequestModel;
import com.library.apigateway.presentationlayer.Catalog.CatalogResponseModel;

import java.util.List;
import java.util.Map;

public interface CatalogService {

    //GET ALL
    List<CatalogResponseModel> findCatalogs();

    //GETONE
    CatalogResponseModel findCatalogById (String catalogId);

    //POST(NEW)
    CatalogResponseModel newCatalog(CatalogRequestModel catalogRequestModel);

    //PUT (UPDATE)
    CatalogResponseModel updateCatalog(CatalogRequestModel catalogRequestModel, String inventoryId);

    //DELETE
    void deleteCatalog(String catalogId);

}
