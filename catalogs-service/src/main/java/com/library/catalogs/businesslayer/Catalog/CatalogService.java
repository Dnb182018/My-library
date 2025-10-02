package com.library.catalogs.businesslayer.Catalog;

import com.library.catalogs.presentation.Catalog.CatalogRequestModel;
import com.library.catalogs.presentation.Catalog.CatalogResponseModel;

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
