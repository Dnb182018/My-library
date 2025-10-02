package com.library.fines.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FineRepositoryIntergrationTest {

    @Autowired
    private FineRepository fineRepository;

    @BeforeEach
    public void setupDb(){
        fineRepository.deleteAll();
    }

    @Test
    public void whenFinesExit_thenReturnAllFines(){
        //arrange
        Fine fine1 = new Fine(LocalDate.parse("2025-01-15"),Status.PAID,new ArrayList<>(Arrays.asList(new FinePayment(BigDecimal.valueOf(150.00),
                Currency.USD,
                LocalDate.parse("2025-01-18"),
                PaymentStatus.COMPLETE,
                PaymentMethod.CREDIT))));

        Fine fine2 = new Fine(LocalDate.parse("2025-01-30"),Status.UNPAID,new ArrayList<>(Arrays.asList(new FinePayment(BigDecimal.valueOf(0.00),
                Currency.USD,
                LocalDate.parse("2025-01-18"),
                PaymentStatus.PENDING,
                PaymentMethod.DEBIT))));

        fineRepository.save(fine2);
        fineRepository.save(fine1);
        long afterSizeDB = fineRepository.count();

        //act
        List<Fine> fineList = fineRepository.findAll();

        //assert
        assertNotNull(fineList);
        assertNotEquals(0,afterSizeDB);
        assertEquals(afterSizeDB, fineList.size());

    }
    //ById
    @Test
    public void whenFineExists_ReturnFineById() {
        //arrange
        Fine fine = new Fine(LocalDate.parse("2025-01-15"),Status.PAID,new ArrayList<>(Arrays.asList(new FinePayment(BigDecimal.valueOf(150.00),
                Currency.USD,
                LocalDate.parse("2025-01-18"),
                PaymentStatus.COMPLETE,
                PaymentMethod.CREDIT))));

        fineRepository.save(fine);
        //act
        Fine savedFine = fineRepository.findFineByFineIdentifier_FineId(fine.getFineIdentifier().getFineId());

        //assert
        assertNotNull(savedFine);
        assertEquals(fine.getFineIdentifier(), savedFine.getFineIdentifier());
        assertEquals(fine.getStatus(), savedFine.getStatus());
        assertEquals(fine.getFinePaymentList(), savedFine.getFinePaymentList());
    }



    //New
    @Test
    public void whenFineEntityIsValid_thenAddFine(){
        //arrange

        Fine fine = new Fine(LocalDate.parse("2025-01-15"),Status.PAID,new ArrayList<>(Arrays.asList(new FinePayment(BigDecimal.valueOf(150.00),
                Currency.USD,
                LocalDate.parse("2025-01-18"),
                PaymentStatus.COMPLETE,
                PaymentMethod.CREDIT))));

        fineRepository.save(fine);


        //act
        Fine savedFine = fineRepository.findFineByFineIdentifier_FineId(fine.getFineIdentifier().getFineId());

        //assert
        assertNotNull(savedFine);
        assertNotNull(savedFine.getId());
        assertNotNull(savedFine.getFineIdentifier());
        assertEquals(fine.getFineIdentifier(), savedFine.getFineIdentifier());
        assertEquals(fine.getStatus(), savedFine.getStatus());


    }



    //UPDATE
    @Test
    public void whenFineIsExist_thenUpdateFine(){
        //arrange

        Fine fine = new Fine(LocalDate.parse("2025-01-15"),Status.UNPAID,new ArrayList<>(Arrays.asList(new FinePayment(BigDecimal.valueOf(150.00),
                Currency.USD,
                LocalDate.parse("2025-01-18"),
                PaymentStatus.COMPLETE,
                PaymentMethod.CREDIT))));

        fineRepository.save(fine);

        //act
        Fine existFine = fineRepository.findFineByFineIdentifier_FineId(fine.getFineIdentifier().getFineId());

        Fine updateFine = new Fine(LocalDate.parse("2025-01-15"),Status.PAID,new ArrayList<>(Arrays.asList(new FinePayment(BigDecimal.valueOf(150.00),
                Currency.USD,
                LocalDate.parse("2025-01-18"),
                PaymentStatus.COMPLETE,
                PaymentMethod.CREDIT))));


        updateFine.setId(existFine.getId());

        Fine savedfine = fineRepository.save(updateFine);

        //assert
        assertNotNull(savedfine);
        assertNotNull(savedfine.getId());
        assertNotNull(savedfine.getFineIdentifier());
        assertNotNull(savedfine.getFineIdentifier().getFineId());
        assertEquals(savedfine.getFineIdentifier().getFineId(),updateFine.getFineIdentifier().getFineId());
//        assertEquals(savedfine.getFinePaymentList(), updateFine.getFinePaymentList());

    }



    //DELETE
    @Test
    public void whenFineExist_thenDeleteFine(){
        //arrange

        Fine fine = new Fine(LocalDate.parse("2025-01-15"),Status.UNPAID,new ArrayList<>(Arrays.asList(new FinePayment(BigDecimal.valueOf(150.00),
                Currency.USD,
                LocalDate.parse("2025-01-18"),
                PaymentStatus.COMPLETE,
                PaymentMethod.CREDIT))));

        fineRepository.save(fine);


        //act
        fineRepository.delete(fine);

        Fine deletedFine = fineRepository.findFineByFineIdentifier_FineId(fine.getFineIdentifier().getFineId());

        //assert
        assertNull(deletedFine);

    }



    //------------NEGATIVE--------------
    @Test
    public void whenFineDoesNotExist_ReturnNull() {
        //act
        Fine savedFine = fineRepository.findFineByFineIdentifier_FineId("b3a9b040-1c2e-4c3b-9127-9f6a001a7c2b");

        //assert
        assertNull(savedFine);
    }














}