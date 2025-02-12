package com.github.brunomndantas.flashscore.api.serviceInterface.config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SwaggerConfigTests {

    private static final String BASE_URL = "http://localhost:8080";


    @Test
    public void shouldReturnOpenApiJson() {
        RestAssured.given()
                .when()
                .get(BASE_URL + "/v3/api-docs")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("openapi", equalTo("3.0.1")); // Ensure OpenAPI version is returned
    }

    @Test
    public void shouldReturnSwaggerUi() {
        RestAssured.given()
                .when()
                .get(BASE_URL + "/swagger-ui.html")
                .then()
                .statusCode(200);
    }

}