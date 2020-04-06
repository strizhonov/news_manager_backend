package com.epam.lab.repository;

import com.epam.lab.model.Client;
import com.epam.lab.model.Client_;
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
public class ClientRepositoryImpl implements ClientRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRepositoryImpl.class);
    private static final String NOT_FOUND_BY_CLIENT_ID_LOGGER_MESSAGE = "Client with id [{}] not found.";
    private static final String UNEXPECTED_BEHAVIOUR_MESSAGE = "Internal logic error. User with [%s] username " +
            "checked before and should exist";


    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Client save(final Client entityToSave) {
        if (clientIdNotExists(entityToSave.getClientId())) {
            entityManager.persist(entityToSave);
            return entityToSave;
        } else {
            return findByClientId(entityToSave.getClientId())
                    .orElseThrow(() -> new IllegalStateException(
                                    String.format(UNEXPECTED_BEHAVIOUR_MESSAGE, entityToSave.getClientId())
                            )
                    );
        }
    }


    @Override
    public Optional<Client> findByClientId(final String clientIdToFind) {
        CriteriaQuery<Client> forClientQuery = createFindByClientIdQuery(clientIdToFind);
        try {
            Client foundClient = entityManager.createQuery(forClientQuery).getSingleResult();
            return Optional.of(foundClient);
        } catch (NoResultException e) {
            LOGGER.info(NOT_FOUND_BY_CLIENT_ID_LOGGER_MESSAGE, clientIdToFind);
            return Optional.empty();
        }
    }


    private boolean clientIdNotExists(final String clientIdToCheck) {
        return !findByClientId(clientIdToCheck).isPresent();
    }


    private CriteriaQuery<Client> createFindByClientIdQuery(final String clientId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> forClientIdQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> clientRoot = forClientIdQuery.from(Client.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(clientRoot.get(Client_.CLIENT_ID), clientId));
        forClientIdQuery.where(predicates.toArray(new Predicate[0]));
        return forClientIdQuery.select(clientRoot);
    }

}
