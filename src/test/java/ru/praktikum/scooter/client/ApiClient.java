package ru.praktikum.scooter.client;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class ApiClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    protected RequestSpecification request() {
        return given()
                .baseUri(BASE_URL)
                .basePath("/api/v1")
                .contentType(ContentType.JSON);
    }
}
