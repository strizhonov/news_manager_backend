package com.epam.lab.repository;

import com.epam.lab.exception.EntityNotFoundException;
import com.epam.lab.model.News;
import com.epam.lab.search.NewsSearchCriteria;
import com.epam.lab.search.NewsSearchQueryCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class NewsRepositoryImpl implements NewsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsRepositoryImpl.class);
    private static final String NOT_FOUND_LOGGER_MESSAGE = "Entity with id {} not found.";
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Entity with id %d not found.";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public News save(final News entityToSave) {
        entityManager.persist(entityToSave);
        return entityToSave;
    }


    @Override
    public News getById(final long entityToGetId) {
        News retrieved = entityManager.find(News.class, entityToGetId);

        if (retrieved == null) {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToGetId);
            throw new EntityNotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, entityToGetId));
        } else {
            return retrieved;
        }
    }


    @Override
    public News update(final News entityToUpdate) {
        if (newsIdExists(entityToUpdate)) {
            return entityManager.merge(entityToUpdate);
        } else {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToUpdate);
            throw new EntityNotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, entityToUpdate.getId()));
        }
    }


    @Override
    public void delete(final News entityToDelete) {
        if (newsIdExists(entityToDelete)) {
            entityManager.remove(entityToDelete);
        } else {
            LOGGER.error(NOT_FOUND_LOGGER_MESSAGE, entityToDelete);
            throw new EntityNotFoundException(String.format(NOT_FOUND_EXCEPTION_MESSAGE, entityToDelete.getId()));
        }
    }


    @Override
    public List<News> findAll() {
        NewsSearchQueryCreator empty = new NewsSearchQueryCreator(new NewsSearchCriteria());
        return search(empty);
    }

    @Override
    public List<News> search(@NonNull final NewsSearchQueryCreator queryCreator) {
        CriteriaQuery<News> forFilteredNewsQuery = queryCreator.createJpaQuery(entityManager.getCriteriaBuilder());
        return entityManager.createQuery(forFilteredNewsQuery).getResultList();
    }

    private boolean newsIdExists(final News entity) {
        return entityManager.find(News.class, entity.getId()) != null;
    }


}
