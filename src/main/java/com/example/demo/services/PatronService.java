package com.example.demo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Patron;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repositories.PatronRepository;

import java.util.List;


/**
 * Service class for managing patron operations.
 */
@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    /**
     * Retrieves all patrons.
     *
     * @return List of all patrons.
     */
    @Cacheable(value = "patrons")
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    /**
     * Retrieves a patron by ID.
     *
     * @param id The ID of the patron to retrieve.
     * @return The patron with the specified ID.
     * @throws ResourceNotFoundException if the patron is not found.
     */
    @Cacheable(value = "patrons", key = "#id")
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron", "id", id));
    }

    /**
     * Adds a new patron.
     *
     * @param patron The patron to add.
     * @return The added patron.
     */
    @CacheEvict(value = "patrons", allEntries = true)
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    /**
     * Updates an existing patron.
     *
     * @param id            The ID of the patron to update.
     * @param patronDetails The updated details of the patron.
     * @return The updated patron.
     * @throws ResourceNotFoundException if the patron is not found.
     */
    @CacheEvict(value = "patrons", key = "#id")
    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron patron = getPatronById(id);
        patron.setName(patronDetails.getName());
        patron.setContactInformation(patronDetails.getContactInformation());
        return patronRepository.save(patron);
    }

    /**
     * Deletes a patron by ID.
     *
     * @param id The ID of the patron to delete.
     * @throws ResourceNotFoundException if the patron is not found.
     */
    @CacheEvict(value = "patrons", key = "#id")
    public void deletePatron(Long id) {
        Patron patron = getPatronById(id);
        patronRepository.delete(patron);
    }
}
