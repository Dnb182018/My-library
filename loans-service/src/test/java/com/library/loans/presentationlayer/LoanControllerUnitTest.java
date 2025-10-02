package com.library.loans.presentationlayer;

import com.library.loans.Utils.exceptions.InvalidInputException;
import com.library.loans.businesslayer.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class LoanControllerUnitTest {


    @Autowired
    LoanController loanController;

    @MockitoBean
    LoanService loanService;

    private final String FOUND_MEMBER_ID = "43b06e4a-c1c5-41d4-a716-446655440002";
    private final String FOUND_LOAN_ID = "9f5d1e7c-6c3b-4a97-9b18-21e7a78e13d1";
    private final String INVALID_MEMBER_ID = "invalid-member-id";


    @Test
    void whenNoLoansExist_thenReturnEmptyList() {
        // arrange
        when(loanService.getAllMemberLoans(FOUND_LOAN_ID)).thenReturn(Collections.emptyList());

        // act
        ResponseEntity<List<LoanResponseModel>> response = loanController.getLoans(FOUND_MEMBER_ID);

        // assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void whenMemberIdIsInvalid_thenThrowInvalidInputException() {
        // act & assert
        InvalidInputException exception = assertThrowsExactly(InvalidInputException.class, () -> {
            loanController.getLoans(INVALID_MEMBER_ID);
        });

        assertEquals("Invalid memberId provided: " + INVALID_MEMBER_ID, exception.getMessage());
        verify(loanService, times(0)).getAllMemberLoans(INVALID_MEMBER_ID);
    }

    @Test
    void whenLoanIsCancelled_thenReturnNoContent() {
        // arrange
        doNothing().when(loanService).deleteLoan(FOUND_MEMBER_ID, FOUND_LOAN_ID);

        // act
        ResponseEntity<Void> response = loanController.deleteLoan(FOUND_MEMBER_ID, FOUND_LOAN_ID);

        // assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(loanService, times(1)).deleteLoan(FOUND_MEMBER_ID, FOUND_LOAN_ID);
    }
}


