package com.library.catalogs.datalayer.Catalog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog,Integer> {

    Catalog findCatalogByCatalogIdentifier_CatalogId(String catalogId);

    void deleteCatalogByCatalogIdentifier_CatalogId(String catalogId);
}
