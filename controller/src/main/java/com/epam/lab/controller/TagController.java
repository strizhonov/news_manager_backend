package com.epam.lab.controller;

import com.epam.lab.dto.TagDto;
import com.epam.lab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService service;

    /**
     * Save Tag into the database.
     * <p>
     * A Tag id value will not be considered during the operation.
     * <p>
     * If the same entity does already present, there is no changes performed.
     * <p>
     * If Tag is successfully added or the same entity does already present,
     * user gets response code "200". Otherwise user gets a code number that
     * corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToSave to insert into the database
     * @return saved tag
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public TagDto save(@RequestBody @Valid final TagDto dtoToSave) {
        return service.create(dtoToSave);
    }


    /**
     * Get Tag from the database by its id value.
     * <p>
     * If Tag is successfully retrieved, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToGetId of the entity to be found
     * @return found tag
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public TagDto get(@PathVariable("id") @Positive(message = "Illegal id, should be greater then 0") final long dtoToGetId) {
        return service.getById(dtoToGetId);
    }


    /**
     * Updates Tag in the database.
     * <p>
     * A Tag id value will be considered during the operation.
     * <p>
     * If Tag is successfully updated, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToUpdate to update
     * @return updated Tag
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public TagDto update(@RequestBody @Valid final TagDto dtoToUpdate) {
        return service.update(dtoToUpdate);
    }


    /**
     * Delete Tag from the database by its id field.
     * <p>
     * If Tag is successfully deleted, user gets response code "200".
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
     * Get List of all Tags in the database.
     * <p>
     * If List of Tags is successfully retrieved, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @return <code>List</code> of all <code>TagDto</code>s in the database.
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<TagDto> findAll() {
        return service.findAll();
    }

}
