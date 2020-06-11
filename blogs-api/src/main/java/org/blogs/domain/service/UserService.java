package org.blogs.domain.service;

import org.blogs.domain.exception.UserException;
import org.blogs.domain.model.User;

import java.util.Optional;

public interface UserService {

    String login(String username, String password) throws UserException;

    void logout(String token) throws UserException;

    void createUser(User user) throws UserException;

    Optional<User> findByUsername(String username) throws UserException;
}
