package com.epam.lab.repository;

import com.epam.lab.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User entityToSave);

    Optional<User> findByUsername(String username);

}
