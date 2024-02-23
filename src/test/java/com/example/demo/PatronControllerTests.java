package com.example.demo;

import com.example.demo.controllers.PatronController;
import com.example.demo.entities.Patron;
import com.example.demo.services.PatronService;

 
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
class PatronControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Test
    public void testAddPatron() throws Exception {
        // Mocking patronService to return the saved patron
        Patron patronToAdd = new Patron(1L, "New Patron","test");
        when(patronService.addPatron(any(Patron.class))).thenReturn(patronToAdd);

        // Performing POST request to add a new patron
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                .content("{\"name\":\"New Patron\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verifying the response
        assertEquals(200, result.getResponse().getStatus());
        verify(patronService, times(1)).addPatron(any(Patron.class));
    }

    @Test
    public void testUpdatePatron() throws Exception {
        // Mocking patronService to return the updated patron
        Patron updatedPatron = new Patron(1L, "Updated Patron","test");
        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(updatedPatron);

        // Performing PUT request to update an existing patron
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/1")
                .content("{\"name\":\"Updated Patron\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verifying the response
        assertEquals(200, result.getResponse().getStatus());
        verify(patronService, times(1)).updatePatron(eq(1L), any(Patron.class));
    }

    @Test
    public void testDeletePatron() throws Exception {
        // Mocking patronService to perform patron deletion
        doNothing().when(patronService).deletePatron(eq(1L));

        // Performing DELETE request to delete an existing patron
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verifying the response
        assertEquals(200, result.getResponse().getStatus());
        verify(patronService, times(1)).deletePatron(eq(1L));
    }
}
