package org.blogs.infrastructure.service;

import org.blogs.domain.exception.UserException;
import org.blogs.domain.model.User;
import org.blogs.domain.repository.UserRepository;
import org.blogs.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String login(String username, String password) throws UserException {
        return userRepository.login(username, password);
    }

    @Override
    public void logout(String token) throws UserException {
        userRepository.logout(token);
    }

    @Override
    public void createUser(User user) throws UserException {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) throws UserException {
        return userRepository.findByUsername(username);
    }
}
