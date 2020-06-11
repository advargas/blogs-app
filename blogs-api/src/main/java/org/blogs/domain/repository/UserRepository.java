package org.blogs.domain.repository;

import org.blogs.domain.exception.UserException;
import org.blogs.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    String login(String email, String password) throws UserException;

    void logout(String token) throws UserException;

    void save(User user) throws UserException;

    Optional<User> findByUsername(String username) throws UserException;
}
