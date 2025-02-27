package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwaggerConfigTests {

    @LocalServerPort
    private int port;


    @Test
    public void shouldReturnOpenApiJson() {
        RestAssured.given()
                .when()
                .get("http://localhost:" + port + "/v3/api-docs")  // Use dynamic port
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("openapi", equalTo("3.0.1")); // Ensure OpenAPI version is returned
    }

    @Test
    public void shouldReturnSwaggerUi() {
        RestAssured.given()
                .when()
                .get("http://localhost:" + port + "/swagger-ui/index.html") // Use dynamic port
                .then()
                .statusCode(200);
    }

}