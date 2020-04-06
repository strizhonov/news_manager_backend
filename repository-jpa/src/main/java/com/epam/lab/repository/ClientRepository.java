package com.epam.lab.repository;

import com.epam.lab.model.Client;

import java.util.Optional;

public interface ClientRepository {

    Client save(Client entityToSave);

    Optional<Client> findByClientId(String clientId);
}
