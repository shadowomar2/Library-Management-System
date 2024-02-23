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
 
import com.example.demo.entities.Patron;
 
import com.example.demo.services.PatronService;

/**
 * Controller class to handle operations related to patrons.
 */
@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    /**
     * Retrieves all patrons.
     *
     * @return List of patrons.
     */
    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    /**
     * Retrieves a patron by ID.
     *
     * @param id The ID of the patron to retrieve.
     * @return ResponseEntity containing the patron if found, or 404 Not Found if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.getPatronById(id);
        return ResponseEntity.ok().body(patron);
    }

    /**
     * Adds a new patron.
     *
     * @param patron The patron to add.
     * @return ResponseEntity containing the added patron.
     */
    @PostMapping
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron) {
        Patron savedPatron = patronService.addPatron(patron);
        return ResponseEntity.ok().body(savedPatron);
    }

    /**
     * Updates an existing patron.
     *
     * @param id             The ID of the patron to update.
     * @param patronDetails  The details of the patron to update.
     * @return ResponseEntity containing the updated patron.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patronDetails) {
        Patron updatedPatron = patronService.updatePatron(id, patronDetails);
        return ResponseEntity.ok().body(updatedPatron);
    }

    /**
     * Deletes a patron by ID.
     *
     * @param id The ID of the patron to delete.
     * @return ResponseEntity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.ok().build();
    }
}