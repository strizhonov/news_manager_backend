package com.epam.lab.service;

import com.epam.lab.dto.UserDto;
import com.epam.lab.exception.UsernameAlreadyExistsException;
import com.epam.lab.model.User;
import com.epam.lab.repository.UserRepository;
import com.epam.lab.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityDtoMapper mapper;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        Optional<User> loadedUser = repository.findByUsername(username);

        return loadedUser.orElseThrow(
                () -> new UsernameNotFoundException(String.format("Username [%s] not found", username))
        );
    }

    @Override
    public UserDto create(final UserDto dtoToCreate) {
        if (usernameIsFree(dtoToCreate.getUsername())) {

            return performCreation(dtoToCreate);

        } else {
            throw new UsernameAlreadyExistsException(
                    String.format("Username [%s] already exists.", dtoToCreate.getUsername()));
        }
    }

    @Override
    public UserDto getById(final long dtoToGetId) {
        throw new UnsupportedOperationException("To be implemented.");
    }

    @Override
    public UserDto update(final UserDto dtoToUpdate) {
        throw new UnsupportedOperationException("To be implemented.");
    }

    @Override
    public void delete(final long dtoToDeleteId) {
        throw new UnsupportedOperationException("To be implemented.");
    }

    @Override
    public List<UserDto> findAll() {
        throw new UnsupportedOperationException("To be implemented.");
    }

    private boolean usernameIsFree(final String username) {
        Optional<User> loadedUser = repository.findByUsername(username);
        return !loadedUser.isPresent();
    }

    private UserDto performCreation(final UserDto dtoToCreate) {
        User toSave = mapper.fromDto(dtoToCreate);
        toSave.setPassword(passwordEncoder.encode(dtoToCreate.getPassword()));
        User saved = repository.save(toSave);
        return mapper.fromEntity(saved);
    }

}