package com.example.demo;

import com.example.demo.controllers.BorrowingController;
import com.example.demo.services.BorrowingService;
 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

 

class BorrowingControllerTests {

    @Mock
    private BorrowingService borrowingService;

    @InjectMocks
    private BorrowingController borrowingController;

    @Test
    public void testBorrowBook() {
        // Mocking the borrowingService.borrowBook() method
        doNothing().when(borrowingService).borrowBook(1L, 1L);

        // Calling the borrowBook() method of the borrowingController
        ResponseEntity<Void> responseEntity = borrowingController.borrowBook(1L, 1L);

        // Verifying that borrowingService.borrowBook() is called once
        verify(borrowingService, times(1)).borrowBook(1L, 1L);

        // Asserting that the response status code is OK (200)
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testReturnBook() {
        // Mocking the borrowingService.returnBook() method
        doNothing().when(borrowingService).returnBook(1L, 1L);

        // Calling the returnBook() method of the borrowingController
        ResponseEntity<Void> responseEntity = borrowingController.returnBook(1L, 1L);

        // Verifying that borrowingService.returnBook() is called once
        verify(borrowingService, times(1)).returnBook(1L, 1L);

        // Asserting that the response status code is OK (200)
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
