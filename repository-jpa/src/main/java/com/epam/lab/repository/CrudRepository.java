package com.epam.lab.repository;

import java.util.List;

/**
 * Common CRUD repository
 *
 * @param <T> type to operate with
 */
public interface CrudRepository<T> {

    /**
     * Save entity in database and return its with generated id
     *
     * @param entityToSave to save in database
     * @return saved object of T with generated id
     */
    T save(T entityToSave);

    /**
     * Get and return entity retrieved from database by id
     *
     * @param entityToGetId of entity to retrieve
     * @return entity retrieved from database by id
     */
    T getById(long entityToGetId);

    /**
     * Update entity in the database
     *
     * @param entityToUpdate to update
     * @return updated entity
     */
    T update(T entityToUpdate);

    /**
     * Delete entity from database
     *
     * @param entityToDelete to delete
     */
    void delete(T entityToDelete);

    /**
     * Retrieve list of all T entities in database
     *
     * @return list of all T entities in database
     */
    List<T> findAll();

}
