package org.blogs.application.controller;

import org.blogs.application.request.UserRequest;
import org.blogs.application.response.ResponseEnvelop;
import org.blogs.domain.exception.UserException;
import org.blogs.domain.model.User;
import org.blogs.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEnvelop<String> createUser(@RequestBody @Valid final UserRequest userRequest) throws UserException {

        userService.createUser(new User(userRequest.getUsername(), userRequest.getFirstName(), userRequest.getLastName(),
                userRequest.getEmail(), userRequest.getPassword()));

        return new ResponseEnvelop<String>(true, "User created successfully: " + userRequest.getUsername());
    }


}
