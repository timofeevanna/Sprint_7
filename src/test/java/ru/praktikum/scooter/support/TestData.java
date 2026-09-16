package ru.praktikum.scooter.support;

import io.qameta.allure.Step;
import ru.praktikum.scooter.model.Courier;
import ru.praktikum.scooter.model.Order;

import java.util.Random;

public final class TestData {
    private TestData() {
    }

    @Step("Подготовить уникальные данные курьера")
    public static Courier newCourier() {
        Random random = new Random();
        String login = "sprint7_" + random.nextInt(1_000_000_000) + "_" + random.nextInt(1_000_000_000);
        String password = "password_" + random.nextInt(1_000_000_000);
        return new Courier(login, password, "TestCourier");
    }

    @Step("Подготовить заказ с цветами {colors}")
    public static Order newOrder(String[] colors) {
        return new Order("Тест", "Самокат", "Тестовая улица, 1", "4",
                "+79990000000", 2, "2026-09-18", "Тестовый заказ", colors);
    }
}
