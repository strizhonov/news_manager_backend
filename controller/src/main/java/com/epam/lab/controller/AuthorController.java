package com.epam.lab.controller;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService service;

    /**
     * Save Author into the database.
     * <p>
     * An Authors id value will not be considered during the operation.
     * <p>
     * If the same entity does already present, there is no changes performed.
     * <p>
     * If Author is successfully added or the same entity does already present,
     * user gets response code "200". Otherwise user gets a code number that
     * corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToSave to insert into the database
     * @return created author
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public AuthorDto save(@RequestBody @Valid final AuthorDto dtoToSave) {
        return service.create(dtoToSave);
    }


    /**
     * Get Author from the database by its id field.
     * <p>
     * If Author is successfully retrieved, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToGetId of the entity to be found
     * @return found author
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public AuthorDto get(@PathVariable("id") @Positive(message = "Illegal id, should be greater then 0") final long dtoToGetId) {
        return service.getById(dtoToGetId);
    }


    /**
     * Updates Author in the database.
     * <p>
     * An Authors id value will be considered during the operation.
     * <p>
     * If Author is successfully updated, user gets response code "200". Otherwise user
     * gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToUpdate to update
     * @return updated author
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public AuthorDto update(@RequestBody @Valid final AuthorDto dtoToUpdate) {
        return service.update(dtoToUpdate);
    }


    /**
     * Delete Author from the database by its id field.
     * <p>
     * If Author is linked with News - it will be deleted from database
     * either.
     * <p>
     * If Author is successfully deleted, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToDeleteId id of the entity to delete
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Positive(message = "Illegal id, should be greater then 0") final long dtoToDeleteId) {
        service.delete(dtoToDeleteId);
    }


    /**
     * Get List of all Authors in the database.
     * <p>
     * If List of Authors is successfully retrieved, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @return List of all Authors in the database.
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<AuthorDto> findAll() {
        return service.findAll();
    }

}
