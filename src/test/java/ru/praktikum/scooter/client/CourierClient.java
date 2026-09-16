package ru.praktikum.scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.scooter.model.Courier;
import ru.praktikum.scooter.model.Credentials;

public class CourierClient extends ApiClient {
    private static final String COURIER_PATH = "/courier";

    @Step("Создать курьера")
    public Response create(Courier courier) {
        return request().body(courier).post(COURIER_PATH);
    }

    @Step("Авторизовать курьера")
    public Response login(Credentials credentials) {
        return request().body(credentials).post(COURIER_PATH + "/login");
    }

    @Step("Удалить курьера с id={id}")
    public Response delete(int id) {
        return request().delete(COURIER_PATH + "/{id}", id);
    }
}
