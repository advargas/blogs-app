## Embedded Keycloak auth server

Keycloak maintains users, roles, clients and performs JWT tokens verification using OpenID Connect (OIDC), an authentication layer on top of OAuth 2.0, acting as an authorization framework.

Before Spring Security provided the option to start up an authorization server from scratch, but this option has been deprecated and new implementations should   connect to an open standard OAuth provider such as Okta, Keycloak, Forgerock, etc.

This Spring Boot Application runs an embedded Keycloak server for testing purposes. For production environments you could set up an installation from scratch of the Keycloak server. For more details, please go to the Keycloak documentation: [Keycloak Documentation](https://www.keycloak.org/docs/latest/getting_started/index.html)

The implementation is based on the article:

[Keycloak Embedded in a Spring Boot Application](https://www.baeldung.com/keycloak-embedded-in-spring-boot-app)

### Run the authorization server

Start the embedded Keycloak authorization server:

```
cd blogs-auth-server
gradle build
gradle bootRun

```

The embedded Keycloak server includes:

1. A realm created with the name Blogsrealm
2. A client created with id blogsapp
3. A pre-created user with username **rest-admin** to create, update and view users

The application loads this pre-configuration from the file: 
[src/main/resources/blogs-realm.json](src/main/resources/blogs-realm.json)

The json file was exported from a real standalone Keycloak instance running, where the configuration was performed through the administration console. The export process includes realm configuration, roles and users:

```
sh standalone.sh 
  -Dkeycloak.migration.realmName=blogsrealm 
  -Dkeycloak.migration.action=export 
  -Dkeycloak.migration.provider=dir 
  -Dkeycloak.migration.dir=/directory

```







