package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.BorrowingService;

/**
 * Controller class to handle HTTP requests related to borrowing books.
 */
@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    /**
     * Endpoint to borrow a book by a patron.
     *
     * @param bookId   The ID of the book to borrow.
     * @param patronId The ID of the patron borrowing the book.
     * @return ResponseEntity with status indicating success or failure.
     */
    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingService.borrowBook(bookId, patronId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to return a borrowed book by a patron.
     *
     * @param bookId   The ID of the book to return.
     * @param patronId The ID of the patron returning the book.
     * @return ResponseEntity with status indicating success or failure.
     */
    @PutMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingService.returnBook(bookId, patronId);
        return ResponseEntity.ok().build();
    }
}
