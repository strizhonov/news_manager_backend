package com.epam.lab.repository;

import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.Tag;
import com.epam.lab.model.Tag_;
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
public class TagRepositoryImpl implements TagRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagRepositoryImpl.class);
    private static final String NOT_FOUND_LOGGER_MESSAGE = "Entity with id {} not found.";
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Entity with id %d not found.";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Tag save(final Tag entityToSave) {
        if (tagNotExists(entityToSave)) {
            entityManager.persist(entityToSave);
            return entityToSave;
        } else {
            return existingTag(entityToSave);
        }
    }


    @Override
    public Tag getById(final long entityToGetId) {
        Tag retrieved = entityManager.find(Tag.class, entityToGetId);

        if (retrieved == null) {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToGetId);
            throw new EntityNotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, entityToGetId));
        } else {
            return retrieved;
        }
    }


    @Override
    public Tag update(final Tag entityToUpdate) {
        if (tagExists(entityToUpdate)) {
            return entityManager.merge(entityToUpdate);
        } else {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToUpdate);
            throw new EntityNotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, entityToUpdate.getId()));
        }
    }


    @Override
    public void delete(final Tag entityToDelete) {
        if (tagExists(entityToDelete)) {
            entityManager.remove(entityToDelete);
        } else {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToDelete);
            throw new EntityNotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, entityToDelete.getId()));
        }
    }


    @Override
    public List<Tag> findAll() {
        CriteriaQuery<Tag> criteriaQuery = createCriteriaQuery();
        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    private boolean tagNotExists(final Tag entityToCheck) {
        try {
            existingTag(entityToCheck);
            return false;
        } catch (NoResultException e) {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToCheck);
            return true;
        }
    }


    private Tag existingTag(final Tag entityToSave) {
        CriteriaQuery<Tag> forTagQuery = createFindByNameQuery(entityToSave.getName());
        return entityManager.createQuery(forTagQuery).getSingleResult();
    }


    private boolean tagExists(final Tag entity) {
        return entityManager.find(Tag.class, entity.getId()) != null;
    }


    private CriteriaQuery<Tag> createCriteriaQuery() {
        CriteriaQuery<Tag> forTagQuery = entityManager.getCriteriaBuilder().createQuery(Tag.class);
        Root<Tag> tagRoot = forTagQuery.from(Tag.class);
        forTagQuery = forTagQuery.select(tagRoot);
        return forTagQuery;
    }


    private CriteriaQuery<Tag> createFindByNameQuery(final String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> forTagQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = forTagQuery.from(Tag.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(tagRoot.get(Tag_.NAME), name));
        forTagQuery.where(predicates.toArray(new Predicate[0]));
        return forTagQuery.select(tagRoot);
    }

}
