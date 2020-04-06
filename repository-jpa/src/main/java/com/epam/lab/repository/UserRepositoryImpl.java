package com.epam.lab.repository;

import com.epam.lab.model.User;
import com.epam.lab.model.User_;
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
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final String NOT_FOUND_BY_USERNAME_LOGGER_MESSAGE = "Entity with username [{}] not found.";
    private static final String UNEXPECTED_BEHAVIOUR_MESSAGE = "Internal logic error. User with [%s] username " +
            "checked before and should exist";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(final User entityToSave) {
        if (usernameNotExists(entityToSave.getUsername())) {
            entityManager.persist(entityToSave);
            return entityToSave;
        } else {
            return findByUsername(entityToSave.getUsername())
                    .orElseThrow(() -> new IllegalStateException(
                                    String.format(UNEXPECTED_BEHAVIOUR_MESSAGE, entityToSave.getUsername())
                            )
                    );
        }
    }

    @Override
    public Optional<User> findByUsername(final String usernameToFind) {
        CriteriaQuery<User> forUserQuery = createFindByUsernameQuery(usernameToFind);
        try {
            User foundUser = entityManager.createQuery(forUserQuery).getSingleResult();
            return Optional.of(foundUser);
        } catch (NoResultException e) {
            LOGGER.info(NOT_FOUND_BY_USERNAME_LOGGER_MESSAGE, usernameToFind);
            return Optional.empty();
        }
    }

    private boolean usernameNotExists(final String usernameToCheck) {
        return !findByUsername(usernameToCheck).isPresent();
    }

    private CriteriaQuery<User> createFindByUsernameQuery(final String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> forUsernameQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = forUsernameQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(userRoot.get(User_.USERNAME), username));
        forUsernameQuery.where(predicates.toArray(new Predicate[0]));
        return forUsernameQuery.select(userRoot);
    }
}
