package com.library.fines.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<Fine,Integer> {

    Fine findFineByFineIdentifier_FineId(String fineId);

    void deleteByFineIdentifier_FineId(String fineId);

}
