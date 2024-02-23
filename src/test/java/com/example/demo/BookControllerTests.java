package com.example.demo;


import com.example.demo.entities.Book;
import com.example.demo.services.BookService;
 
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testAddBook() throws Exception {
        // Mocking bookService to return the saved book
    	Book bookToAdd = new Book(1L, "New Book", "Author Name", 2024, "1234567890");

        when(bookService.addBook(any(Book.class))).thenReturn(bookToAdd);

        // Performing POST request to add a new book
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .content("{\"title\":\"New Book\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verifying the response
        assertEquals(200, result.getResponse().getStatus());
        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Mocking bookService to return the updated book
        Book updatedBook  = new Book(1L, "New Book", "Author Name", 2024, "1234567890");

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);

        // Performing PUT request to update an existing book
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                .content("{\"title\":\"Updated Book\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verifying the response
        assertEquals(200, result.getResponse().getStatus());
        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class));
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Mocking bookService to perform book deletion
        doNothing().when(bookService).deleteBook(eq(1L));

        // Performing DELETE request to delete an existing book
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verifying the response
        assertEquals(200, result.getResponse().getStatus());
        verify(bookService, times(1)).deleteBook(eq(1L));
    }
}
