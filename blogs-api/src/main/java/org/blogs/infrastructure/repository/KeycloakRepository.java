package org.blogs.infrastructure.repository;

import org.blogs.domain.exception.UserException;
import org.blogs.domain.model.User;
import org.blogs.domain.repository.UserRepository;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Repository
public class KeycloakRepository implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakRepository.class);

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin-username}")
    private String username;

    @Value("${keycloak.admin-password}")
    private String password;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.pool-size}")
    private Integer poolSize;

    private Keycloak adminClient;

    @PostConstruct
    private void init() {
        this.adminClient = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username(username)
                .password(password)
                .clientId(clientId)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(poolSize).build())
                .build();
    }

    @Override
    public String login(String username, String password) throws UserException {
        Keycloak kc = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .username(username)
                .password(password)
                .clientId(clientId)
                .build();

        AccessTokenResponse token = kc.tokenManager().grantToken();
        LOGGER.debug("Access token generated for user: " + username);
        return token.getToken();
    }

    @Override
    public void logout(String token) throws UserException {

        adminClient.tokenManager().invalidate(token);
    }

    @Override
    public void save(User user) throws UserException {

        Optional<User> oldUser = findByUsername(user.getUsername());

        if (!oldUser.isPresent()) {

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(user.getPassword());
            credential.setTemporary(false);

            UserRepresentation kcUser = new UserRepresentation();
            kcUser.setUsername(user.getUsername());
            kcUser.setFirstName(user.getFirstName());
            kcUser.setLastName(user.getLastName());
            kcUser.setEmail(user.getEmail());
            kcUser.setCredentials(asList(credential));
            kcUser.setEnabled(true);

            Response result = adminClient.realm(realm).users().create(kcUser);
            if (result.getStatus() != 201) {
                LOGGER.error("Error creating user in Keycloak");
                LOGGER.error(result.getStatusInfo().getReasonPhrase());
                throw new UserException(result.getStatusInfo().getReasonPhrase());
            }
        } else {
            throw new UserException("User with username " + user.getUsername() + " is already registered.");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<UserRepresentation> users = adminClient.realm(realm).users().search(username);
        if (users != null && !users.isEmpty()) {
            UserRepresentation kcUser = users.iterator().next();
            return Optional.of(new User(kcUser.getUsername(), kcUser.getFirstName(), kcUser.getLastName(),
                    kcUser.getEmail(), null));
        }
        return Optional.empty();
    }
}
