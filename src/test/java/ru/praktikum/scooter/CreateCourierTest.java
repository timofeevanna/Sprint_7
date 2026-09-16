package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.praktikum.scooter.model.Courier;
import ru.praktikum.scooter.support.CourierTestBase;
import ru.praktikum.scooter.support.TestData;

import static ru.praktikum.scooter.support.ResponseChecks.*;

public class CreateCourierTest extends CourierTestBase {
    @Test
    @DisplayName("Курьер создаётся со всеми полями")
    public void shouldCreateCourier() {
        checkOk(courierClient.create(courier), 201);
    }

    @Test
    @DisplayName("Курьер создаётся без необязательного firstName")
    public void shouldCreateCourierWithoutFirstName() {
        Courier request = new Courier(courier.getLogin(), courier.getPassword(), null);
        checkOk(courierClient.create(request), 201);
    }

    @Test
    @DisplayName("Курьер создаётся с пустым firstName")
    public void shouldCreateCourierWithEmptyFirstName() {
        Courier request = new Courier(courier.getLogin(), courier.getPassword(), "");
        checkOk(courierClient.create(request), 201);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void shouldRejectDuplicateCourier() {
        createTestCourier();
        checkError(courierClient.create(courier), 409, LOGIN_ALREADY_USED);
    }

    @Test
    @DisplayName("Занятый логин отклоняется и при других пароле и имени")
    public void shouldRejectExistingLoginWithDifferentData() {
        createTestCourier();
        Courier otherCourier = TestData.newCourier();
        Courier request = new Courier(courier.getLogin(), otherCourier.getPassword(), "AnotherCourier");
        checkError(courierClient.create(request), 409, LOGIN_ALREADY_USED);
    }

    @Test
    @DisplayName("Создание без поля login возвращает 400")
    public void shouldRejectMissingLogin() {
        Courier request = new Courier(null, courier.getPassword(), courier.getFirstName());
        checkError(courierClient.create(request), 400, CREATE_DATA_MISSING);
    }

    @Test
    @DisplayName("Создание без поля password возвращает 400")
    public void shouldRejectMissingPassword() {
        Courier request = new Courier(courier.getLogin(), null, courier.getFirstName());
        checkError(courierClient.create(request), 400, CREATE_DATA_MISSING);
    }

    @Test
    @DisplayName("Создание с пустым login возвращает 400")
    public void shouldRejectEmptyLogin() {
        Courier request = new Courier("", courier.getPassword(), courier.getFirstName());
        checkError(courierClient.create(request), 400, CREATE_DATA_MISSING);
    }

    @Test
    @DisplayName("Создание с пустым password возвращает 400")
    public void shouldRejectEmptyPassword() {
        Courier request = new Courier(courier.getLogin(), "", courier.getFirstName());
        checkError(courierClient.create(request), 400, CREATE_DATA_MISSING);
    }
}
