package com.library.loans.mappinglayer;

import com.library.loans.dataacceslayer.Loan;
import com.library.loans.dataacceslayer.LoanIdentifier;
import com.library.loans.domainClientLayer.catalogs.CatalogModel;
import com.library.loans.domainClientLayer.fines.FineModel;
import com.library.loans.domainClientLayer.members.MemberModel;
import com.library.loans.presentationlayer.LoanRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {

            @Mapping(target = "id", ignore = true)



    Loan requestModelToEntity(LoanRequestModel loanRequestModel, LoanIdentifier loanIdentifier,
                              MemberModel memberModel, CatalogModel catalogModel,
                              FineModel fineModel);
    }


