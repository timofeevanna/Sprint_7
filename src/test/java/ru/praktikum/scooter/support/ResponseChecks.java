package ru.praktikum.scooter.support;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static org.hamcrest.Matchers.*;

public final class ResponseChecks {
    public static final String CREATE_DATA_MISSING = "Недостаточно данных для создания учетной записи";
    public static final String LOGIN_DATA_MISSING = "Недостаточно данных для входа";
    public static final String COURIER_NOT_FOUND = "Учетная запись не найдена";
    public static final String LOGIN_ALREADY_USED = "Этот логин уже используется. Попробуйте другой.";

    private ResponseChecks() {
    }

    @Step("Проверить код {status} и ok=true")
    public static void checkOk(Response response, int status) {
        response.then().statusCode(status).contentType(ContentType.JSON).body("ok", equalTo(true));
    }

    @Step("Проверить код ошибки {status} и сообщение: {message}")
    public static void checkError(Response response, int status, String message) {
        response.then().statusCode(status).contentType(ContentType.JSON).body("message", equalTo(message));
    }

    @Step("Проверить успешную авторизацию и получить id курьера")
    public static int checkLogin(Response response) {
        return response.then().statusCode(200).contentType(ContentType.JSON)
                .body("id", allOf(instanceOf(Integer.class), greaterThan(0)))
                .extract().path("id");
    }

    @Step("Проверить, что авторизация вернула id={expectedId}")
    public static void checkCourierId(Response response, int expectedId) {
        response.then().statusCode(200).contentType(ContentType.JSON).body("id", equalTo(expectedId));
    }

    @Step("Проверить создание заказа и положительный track")
    public static void checkOrderCreated(Response response) {
        response.then().statusCode(201).contentType(ContentType.JSON)
                .body("track", allOf(instanceOf(Integer.class), greaterThan(0)));
    }

    @Step("Проверить код 200, массив orders и параметры страницы")
    public static void checkOrdersList(Response response, int limit, int page) {
        response.then().statusCode(200).contentType(ContentType.JSON)
                .body("orders", instanceOf(List.class))
                .body("orders.size()", lessThanOrEqualTo(limit))
                .body("pageInfo.page", equalTo(page))
                .body("pageInfo.limit", equalTo(limit));
    }
}
