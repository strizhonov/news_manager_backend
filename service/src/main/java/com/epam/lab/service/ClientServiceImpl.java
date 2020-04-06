package com.epam.lab.service;

import com.epam.lab.dto.ClientDto;
import com.epam.lab.model.Client;
import com.epam.lab.repository.ClientRepository;
import com.epam.lab.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityDtoMapper mapper;

    @Override
    public ClientDto create(final ClientDto dtoToCreate) {
        if (clientIdIsFree(dtoToCreate.getClientId())) {

            return performCreation(dtoToCreate);

        } else {
            throw new ClientRegistrationException(
                    String.format("ClientId [%s] already exists.", dtoToCreate.getClientId()));
        }
    }

    @Override
    public ClientDto getById(final long dtoToGetId) {
        throw new UnsupportedOperationException("To be implemented.");
    }

    @Override
    public ClientDto update(final ClientDto dtoToUpdate) {
        throw new UnsupportedOperationException("To be implemented.");
    }

    @Override
    public void delete(final long dtoToDeleteId) {
        throw new UnsupportedOperationException("To be implemented.");
    }

    @Override
    public List<ClientDto> findAll() {
        throw new UnsupportedOperationException("To be implemented.");
    }

    @Override
    public ClientDetails loadClientByClientId(final String clientId) throws ClientRegistrationException {
        Optional<Client> loadedClient = repository.findByClientId(clientId);

        return loadedClient.orElseThrow(
                () -> new ClientRegistrationException(String.format("ClientId [%s] not found", clientId))
        );
    }

    private boolean clientIdIsFree(final String clientId) {
        Optional<Client> loadedClient = repository.findByClientId(clientId);
        return !loadedClient.isPresent();
    }

    private ClientDto performCreation(final ClientDto dtoToCreate) {
        Client toSave = mapper.fromDto(dtoToCreate);
        toSave.setClientSecret(passwordEncoder.encode(dtoToCreate.getClientSecret()));
        Client saved = repository.save(toSave);
        return mapper.fromEntity(saved);
    }

}
