package ru.praktikum.scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.scooter.model.Credentials;
import ru.praktikum.scooter.support.CourierTestBase;
import ru.praktikum.scooter.support.TestData;

import static ru.praktikum.scooter.support.ResponseChecks.*;

public class LoginCourierTest extends CourierTestBase {
    @Before
    @Step("Зарегистрировать курьера перед тестом авторизации")
    public void registerCourier() {
        createTestCourier();
    }

    @Test
    @DisplayName("Авторизация возвращает id созданного курьера")
    public void shouldLoginCourier() {
        checkCourierId(courierClient.login(Credentials.from(courier)), courierId);
    }

    @Test
    @DisplayName("Авторизация с неверным логином возвращает 404")
    public void shouldRejectWrongLogin() {
        Credentials request = new Credentials(courier.getLogin() + "_wrong", courier.getPassword());
        checkError(courierClient.login(request), 404, COURIER_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация с неверным паролем возвращает 404")
    public void shouldRejectWrongPassword() {
        Credentials request = new Credentials(courier.getLogin(), courier.getPassword() + "_wrong");
        checkError(courierClient.login(request), 404, COURIER_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация без поля login возвращает 400")
    public void shouldRejectMissingLogin() {
        Credentials request = new Credentials(null, courier.getPassword());
        checkError(courierClient.login(request), 400, LOGIN_DATA_MISSING);
    }

    @Test
    @DisplayName("Авторизация без поля password возвращает 400")
    public void shouldRejectMissingPassword() {
        Credentials request = new Credentials(courier.getLogin(), null);
        checkError(courierClient.login(request), 400, LOGIN_DATA_MISSING);
    }

    @Test
    @DisplayName("Авторизация с пустым login возвращает 400")
    public void shouldRejectEmptyLogin() {
        Credentials request = new Credentials("", courier.getPassword());
        checkError(courierClient.login(request), 400, LOGIN_DATA_MISSING);
    }

    @Test
    @DisplayName("Авторизация с пустым password возвращает 400")
    public void shouldRejectEmptyPassword() {
        Credentials request = new Credentials(courier.getLogin(), "");
        checkError(courierClient.login(request), 400, LOGIN_DATA_MISSING);
    }

    @Test
    @DisplayName("Несуществующий курьер не может авторизоваться")
    public void shouldRejectNonexistentCourier() {
        Credentials request = Credentials.from(TestData.newCourier());
        checkError(courierClient.login(request), 404, COURIER_NOT_FOUND);
    }
}
