package org.blogs.application.controller;

import org.blogs.application.request.LoginRequest;
import org.blogs.application.response.LoginResponse;
import org.blogs.application.response.ResponseEnvelop;
import org.blogs.domain.exception.UserException;
import org.blogs.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEnvelop<LoginResponse> login(@RequestBody @Valid final LoginRequest loginRequest) throws UserException {

        String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());

        return new ResponseEnvelop<LoginResponse>(true, new LoginResponse(token));
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEnvelop<String> logout(@RequestHeader("Authorization") String authorization) throws UserException {

        String token = authorization.replace("Bearer ", "");
        userService.logout(token);

        return new ResponseEnvelop<String>(true, "User logged out successfully");
    }
}
