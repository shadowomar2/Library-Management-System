package com.example.demo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
 
import com.example.demo.entities.Book;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repositories.BookRepository;

import java.util.List;
/**
 * Service class for managing books.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Retrieves all books.
     *
     * @return List of all books.
     */
    @Cacheable(value = "books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The book with the specified ID.
     * @throws ResourceNotFoundException if the book with the specified ID is not found.
     */
    @Cacheable(value = "books", key = "#id")
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    /**
     * Adds a new book.
     *
     * @param book The book to add.
     * @return The added book.
     */
    @CacheEvict(value = "books", allEntries = true)
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Updates an existing book.
     *
     * @param id           The ID of the book to update.
     * @param bookDetails  The updated details of the book.
     * @return The updated book.
     * @throws ResourceNotFoundException if the book with the specified ID is not found.
     */
    @CacheEvict(value = "books", key = "#id")
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublicationYear(bookDetails.getPublicationYear());
        book.setIsbn(bookDetails.getIsbn());
        return bookRepository.save(book);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete.
     * @throws ResourceNotFoundException if the book with the specified ID is not found.
     */
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
}