package com.library.apigateway.businesslayer.Catalog;


import com.library.apigateway.domainclientlayer.catalogs.CatalogsServiceClient;
import com.library.apigateway.mappinglayer.catalog.CatalogResponseMapper;
import com.library.apigateway.presentationlayer.Catalog.CatalogRequestModel;
import com.library.apigateway.presentationlayer.Catalog.CatalogResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {


    private final CatalogsServiceClient catalogsServiceClient;

    private final CatalogResponseMapper catalogResponseMapper;

    @Override
    public List<CatalogResponseModel> findCatalogs() {
        return catalogResponseMapper.entityListToResponseModelList(catalogsServiceClient.getAllCatalogs());
    }

    @Override
    public CatalogResponseModel findCatalogById(String catalogId) {
        return catalogResponseMapper.entityToResponseModel(catalogsServiceClient.getCatalogsById(catalogId));
    }

    @Override
    public CatalogResponseModel newCatalog(CatalogRequestModel catalogRequestModel) {
        return catalogResponseMapper.entityToResponseModel(catalogsServiceClient.createCatalog(catalogRequestModel));
    }

    @Override
    public CatalogResponseModel updateCatalog(CatalogRequestModel catalogRequestModel, String catlogId) {
        return catalogResponseMapper.entityToResponseModel(catalogsServiceClient.updateCatalog(catlogId,catalogRequestModel));
    }

    @Override
    public void deleteCatalog(String catalogId) {
        catalogsServiceClient.deleteCatalog(catalogId);
    }
}
