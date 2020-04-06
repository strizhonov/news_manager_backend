package com.epam.lab.service;

import java.util.List;

/**
 * Common com.epam.lab.service interface for CRUD operations with dto
 *
 * @param <T> object to interact with representation layer
 */
public interface AppService<T> {

    /**
     * Save T object in repository
     *
     * @param dtoToCreate to save in repository
     * @return created T object
     */
    T create(T dtoToCreate);

    /**
     * Get T object by id
     *
     * @param dtoToGetId of object to get
     * @return retrieved DTO object
     */
    T getById(long dtoToGetId);

    /**
     * Update T object
     *
     * @param dtoToUpdate dto to update
     * @return updated T object
     */
    T update(T dtoToUpdate);

    /**
     * Delete T object by id
     *
     * @param dtoToDeleteId of T to delete
     */
    void delete(long dtoToDeleteId);

    /**
     * Return list of all T objects in repository
     *
     * @return list of all T objects in repository
     */
    List<T> findAll();

}
