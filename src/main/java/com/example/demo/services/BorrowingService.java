package com.example.demo.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Book;
import com.example.demo.entities.BorrowingRecord;
import com.example.demo.entities.Patron;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.BorrowingRecordRepository;
import com.example.demo.repositories.PatronRepository;

/**
 * Service class for managing book borrowing operations.
 */
@Service
public class BorrowingService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    /**
     * Borrow a book for a patron.
     *
     * @param bookId   The ID of the book to borrow.
     * @param patronId The ID of the patron borrowing the book.
     * @throws ResourceNotFoundException if the book or patron is not found.
     */
    @Transactional
    public void borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron", "id", patronId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        borrowingRecordRepository.save(borrowingRecord);
    }

    /**
     * Return a borrowed book.
     *
     * @param bookId   The ID of the book to return.
     * @param patronId The ID of the patron returning the book.
     * @throws ResourceNotFoundException if the borrowing record is not found or if the book has already been returned.
     */
    @Transactional
    public void returnBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);

        if (borrowingRecord == null) {
            throw new ResourceNotFoundException("BorrowingRecord", "bookId=" + bookId, " and patronId=" + patronId);
        }

        if (borrowingRecord.getReturnDate() != null) {
            throw new ResourceNotFoundException("Book has already been returned", "bookId=" + bookId, " and patronId=" + patronId);
        }

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);
    }
}
