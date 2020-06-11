## Secured REST API

The secured REST API is an oauth2-resource-server and publishes the endpoints to be consumed.

### Run the Spring App

```
cd blogs-api
gradle build
gradle rootRun

```

The Backend application exposes authentication endpoints and some other endpoints, as an example, with CRUD operations to manage posts and check secured endpoints with a JWT token:

* Login user
* Logout user
* Create user
* Create/Read/Update/Delete Posts

The user accounts are maintained in Keycloak, the Posts are stored by the application in an H2 in-memory database embedded in the Spring Boot application.

For authenticated endpoints, please make sure you update the Authorization header with the access token returned in the login endpoint response.

All validations are performed with Java Bean Validation.

The Keycloak repository class uses a Keycloak client to perform the administration operations over user accounts, stablishing a connection with the server with the **rest-admin** user.

The implementation follows an Hexagonal architecture where the domain classes are isolated and free of all annotations or references to specific-vendor classes, so you could change the OAuth2 provider, or even the backend framework more easily as all the specific-vendor code is aggregated in separated internal packages. So you can find 3 main packages:

1. **application:** All classes exposed to outside, to be consumed by external clients, it includes REST Controllers and their models of request and response.
2. **domain**: Core classes including model and business logic.
3. **infrastructure**: It includes configuration classes and all artifacts the application needs to work.

### Important Links:

* API Health: [http://localhost:8082/actuator/health](http://localhost:8082/actuator/health)
* Swagger Info: [http://localhost:8081/blogs/swagger-ui.html](http://localhost:8081/blogs/swagger-ui.html)
* Postman File: [Login-Example.postman_collection.json](../resources/Login-Example.postman_collection.json)


