package com.library.catalogs.businesslayer.Catalog;


import com.library.catalogs.Utils.exceptions.InvalidInputException;
import com.library.catalogs.Utils.exceptions.NotFoundException;
import com.library.catalogs.datalayer.Catalog.Catalog;
import com.library.catalogs.datalayer.Catalog.CatalogRepository;
import com.library.catalogs.mappinglayer.Catalog.CatalogRequestMapper;
import com.library.catalogs.mappinglayer.Catalog.CatalogResponseMapper;
import com.library.catalogs.presentation.Catalog.CatalogRequestModel;
import com.library.catalogs.presentation.Catalog.CatalogResponseModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;
    private final CatalogResponseMapper catalogResponseMapper;
    private final CatalogRequestMapper catalogRequestMapper;



    //GET ALL
    @Override
    public List<CatalogResponseModel> findCatalogs() {
        List<Catalog> catalogList = catalogRepository.findAll();

        return catalogResponseMapper.entityListToResponseModelList(catalogList);

    }






    //GET ONE
    @Override
    public CatalogResponseModel findCatalogById(String catalogId) {
        Catalog catalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);

        if (catalog == null){
            throw new NotFoundException("Unknown catalogId: " + catalogId);
        }
        return catalogResponseMapper.entityToResponseModel(catalog);
    }



    //POST
    @Override
    public CatalogResponseModel newCatalog(CatalogRequestModel catalogRequestModel) {
        Catalog tobeSaved = catalogRequestMapper.requestModelToEntity(catalogRequestModel);

        tobeSaved.getCatalogIdentifier().getCatalogId();

        Catalog savedCatalog = catalogRepository.save(tobeSaved);

        return catalogResponseMapper.entityToResponseModel(savedCatalog);
    }




    //UPDATE
    @Override
    public CatalogResponseModel updateCatalog(CatalogRequestModel catalogRequestModel, String catalogId) {
        Catalog foundCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);

        if (foundCatalog == null){
            throw new NotFoundException("Unknown catalogId: " + catalogId);
        }

        Catalog tobeSaved = catalogRequestMapper.requestModelToEntity(catalogRequestModel);

        tobeSaved.setId(foundCatalog.getId());

        Catalog savedCatalog = catalogRepository.save(tobeSaved);

        return catalogResponseMapper.entityToResponseModel(savedCatalog);
    }




    //DELETE
    @Transactional
    public void deleteCatalog(String catalogId) {
        Catalog foundCatalog = catalogRepository.findCatalogByCatalogIdentifier_CatalogId(catalogId);

        if (foundCatalog == null){
            throw new NotFoundException("Unknown catalogId: " + catalogId);
        }

        else {
            this.catalogRepository.deleteCatalogByCatalogIdentifier_CatalogId(catalogId);
        }


    }
}
