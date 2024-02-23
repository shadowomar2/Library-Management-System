package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Book;
import com.example.demo.services.BookService;
/**
 * Controller class to handle HTTP requests related to books.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Get all books.
     *
     * @return List of all books.
     */
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Get a book by its ID.
     *
     * @param id The ID of the book.
     * @return The book with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok().body(book);
    }

    /**
     * Add a new book.
     *
     * @param book The book to add.
     * @return The saved book.
     */
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.ok().body(savedBook);
    }

    /**
     * Update an existing book.
     *
     * @param id           The ID of the book to update.
     * @param bookDetails  The details of the book to update.
     * @return The updated book.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok().body(updatedBook);
    }

    /**
     * Delete a book by its ID.
     *
     * @param id The ID of the book to delete.
     * @return ResponseEntity with status indicating success or failure.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}