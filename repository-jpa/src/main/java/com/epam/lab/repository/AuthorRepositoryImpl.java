package com.epam.lab.repository;

import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Author;
import com.epam.lab.model.Author_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRepositoryImpl.class);
    private static final String NOT_FOUND_LOGGER_MESSAGE = "Entity with id {} not found.";
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Entity with id %d not found.";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Author save(final Author entityToSave) {
        if (authorNotExists(entityToSave)) {
            entityManager.persist(entityToSave);
            return entityToSave;
        } else {
            return existingAuthor(entityToSave);
        }
    }


    @Override
    public Author getById(final long entityToGetId) {
        Author retrieved = entityManager.find(Author.class, entityToGetId);

        if (retrieved == null) {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToGetId);
            throw new EntityNotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, entityToGetId));
        } else {
            return retrieved;
        }
    }


    @Override
    public Author update(final Author entityToUpdate) {
        if (authorIdExists(entityToUpdate)) {
            return entityManager.merge(entityToUpdate);
        } else {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToUpdate);
            throw new EntityNotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, entityToUpdate.getId()));
        }
    }


    @Override
    public void delete(final Author entityToDelete) {
        if (authorIdExists(entityToDelete)) {
            entityManager.remove(entityToDelete);
        } else {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToDelete);
            throw new EntityNotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, entityToDelete.getId()));
        }
    }


    @Override
    public List<Author> findAll() {
        CriteriaQuery<Author> forAllAuthorsQuery = createFindAllQuery();
        return entityManager.createQuery(forAllAuthorsQuery).getResultList();
    }


    private boolean authorNotExists(final Author entityToCheck) {
        try {
            existingAuthor(entityToCheck);
            return false;
        } catch (NoResultException e) {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToCheck);
            return true;
        }
    }


    private Author existingAuthor(final Author entityToSave) {
        CriteriaQuery<Author> forAuthorQuery = createFindByNameAndSurnameQuery(entityToSave.getName(), entityToSave.getSurname());
        return entityManager.createQuery(forAuthorQuery).getSingleResult();
    }


    private boolean authorIdExists(final Author entity) {
        return entityManager.find(Author.class, entity.getId()) != null;
    }


    private CriteriaQuery<Author> createFindAllQuery() {
        CriteriaQuery<Author> forAuthorQuery = entityManager.getCriteriaBuilder().createQuery(Author.class);
        Root<Author> authorRoot = forAuthorQuery.from(Author.class);
        forAuthorQuery = forAuthorQuery.select(authorRoot);
        return forAuthorQuery;
    }


    private CriteriaQuery<Author> createFindByNameAndSurnameQuery(final String name, final String surname) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> forAuthorQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> authorRoot = forAuthorQuery.from(Author.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(authorRoot.get(Author_.NAME), name));
        predicates.add(criteriaBuilder.equal(authorRoot.get(Author_.SURNAME), surname));
        forAuthorQuery.where(predicates.toArray(new Predicate[0]));
        return forAuthorQuery.select(authorRoot);
    }


}
