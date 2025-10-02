package com.library.catalogs.mappinglayer.Catalog;

import com.library.catalogs.datalayer.Catalog.Catalog;
import com.library.catalogs.presentation.Catalog.CatalogRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CatalogRequestMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    Catalog requestModelToEntity(CatalogRequestModel catalogRequestModel);
}
