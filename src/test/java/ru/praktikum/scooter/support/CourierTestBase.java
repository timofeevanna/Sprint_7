package ru.praktikum.scooter.support;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import ru.praktikum.scooter.client.CourierClient;
import ru.praktikum.scooter.model.Courier;
import ru.praktikum.scooter.model.Credentials;

import static ru.praktikum.scooter.support.ResponseChecks.*;

public abstract class CourierTestBase {
    protected final CourierClient courierClient = new CourierClient();
    protected Courier courier;
    protected Integer courierId;

    @Before
    @Step("Подготовить данные для независимого теста")
    public void prepareCourierData() {
        courier = TestData.newCourier();
    }

    @Step("Создать курьера для проверки авторизации")
    protected void createTestCourier() {
        checkOk(courierClient.create(courier), 201);
        courierId = checkLogin(courierClient.login(Credentials.from(courier)));
    }

    @After
    @Step("Удалить созданного в тесте курьера")
    public void cleanUpCourier() {
        if (courier == null) {
            return;
        }
        if (courierId == null) {
            Response loginResponse = courierClient.login(Credentials.from(courier));
            if (loginResponse.statusCode() == 404) {
                checkError(loginResponse, 404, COURIER_NOT_FOUND);
                return;
            }
            courierId = checkLogin(loginResponse);
        }
        checkOk(courierClient.delete(courierId), 200);
    }
}
