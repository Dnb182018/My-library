package com.library.fines.businesslayer;
import com.library.fines.Utils.exceptions.InvalidEnumException;
import com.library.fines.Utils.exceptions.InvalidInputException;
import com.library.fines.Utils.exceptions.NotFoundException;
import com.library.fines.datalayer.*;
import com.library.fines.mappinglayer.FineRequestMapper;
import com.library.fines.mappinglayer.FineResponseMapper;
import com.library.fines.presentationlayer.FineRequestModel;
import com.library.fines.presentationlayer.FineResponseModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService{

    private final FineRepository fineRepository;
    private final FineRequestMapper fineRequestMapper;
    private final FineResponseMapper fineResponseMapper;


    //GET ALL
    @Override
    public List<FineResponseModel> findFines() {
        List<Fine> fineList = fineRepository.findAll();

        return fineResponseMapper.entityListToResponseModelList(fineList);
    }



    //GETONE
    @Override
    public FineResponseModel findFineByID(String fineId) {
        Fine fine = fineRepository.findFineByFineIdentifier_FineId(fineId);

        if (fine == null){
            throw new NotFoundException("Unknown fineId: " + fineId);
        }
        return fineResponseMapper.entityToResponseModel(fine);
    }



    //POST
    @Override
    public FineResponseModel newFine(FineRequestModel fineRequestModel) {
        FineIdentifier fineIdentifier = new FineIdentifier();

        List<FinePayment> finePaymentList = fineRequestModel.getFinePayment();

        Fine tobeSaved = fineRequestMapper.requestModelToEntity(fineRequestModel,fineIdentifier,finePaymentList);

        Fine savedFine = fineRepository.save(tobeSaved);

        return fineResponseMapper.entityToResponseModel(savedFine);
    }






    //UPDATE
    @Override
    public FineResponseModel updateFine(FineRequestModel fineRequestModel, String fineId) {
        Fine fine = fineRepository.findFineByFineIdentifier_FineId(fineId);

        if (fine == null){
            throw new NotFoundException("Unknown fineId: " + fineId);
        }
        List<FinePayment> finePaymentList = fineRequestModel.getFinePayment();

        Fine tobeSaved = fineRequestMapper.requestModelToEntity(fineRequestModel,fine.getFineIdentifier(),finePaymentList);

        tobeSaved.setId(fine.getId());

        Fine savedFine = fineRepository.save(tobeSaved);

//        //---------------------------------------------------
//
//        //TODO: Fine "status" will update according to PaymentStatus
//        if(savedFine.getFinePayment().getStatus() == PaymentStatus.PENDING || savedFine.getFinePayment().getStatus() == PaymentStatus.FAILED){
//            updateFineStatus(savedFine.getFineIdentifier().getFineId(),Status.UNPAID);
//        }
//        if(savedFine.getFinePayment().getStatus() == PaymentStatus.COMPLETE ){
//            updateFineStatus(savedFine.getFineIdentifier().getFineId(),Status.PAID);
//        }
//
//        //------------------------------------------------------
        return fineResponseMapper.entityToResponseModel(savedFine);
    }






    //DELETE
    @Transactional
    public void deleteFine(String fineId) {
        Fine fine = fineRepository.findFineByFineIdentifier_FineId(fineId);

        if (fine == null){
            throw new NotFoundException("Unknown fineId: " + fineId);
        }
        else{
            this.fineRepository.deleteByFineIdentifier_FineId(fineId);
        }
    }






//    //Update Status according Payment Status
//    @Override
//    public Boolean updateFineStatus(String fineId, Status status) {
//        Fine fine = fineRepository.findFineByFineIdentifier_FineId(fineId);
//
//        if (fine == null){
//            throw new InvalidInputException("Unknown fineId: " + fineId);
//        }
//
//        fine.setStatus(status);
//
//        Fine savedFine = fineRepository.save(fine);
//
//        if(savedFine!= null){
//            return true;
//        }
//        return false;
//    }
}
