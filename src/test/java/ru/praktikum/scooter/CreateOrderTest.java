package ru.praktikum.scooter;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.scooter.client.OrderClient;
import ru.praktikum.scooter.model.Order;
import ru.praktikum.scooter.support.TestData;

import java.util.Arrays;
import java.util.Collection;

import static ru.praktikum.scooter.support.ResponseChecks.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrderClient orderClient = new OrderClient();
    private final String[] colors;
    private Order order;
    private Integer track;

    public CreateOrderTest(String scenario, String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> colors() {
        return Arrays.asList(new Object[][] {
                {"Чёрный", new String[] {"BLACK"}},
                {"Серый", new String[] {"GREY"}},
                {"Оба цвета", new String[] {"BLACK", "GREY"}},
                {"Пустой список цветов", new String[] {}},
                {"Поле color отсутствует", null}
        });
    }

    @Before
    @Step("Подготовить новый заказ перед тестом")
    public void prepareOrder() {
        order = TestData.newOrder(colors);
    }

    @Test
    @DisplayName("Заказ создаётся для выбранного варианта цвета")
    public void shouldCreateOrderWithSelectedColors() {
        Response response = orderClient.create(order);
        track = response.path("track");
        checkOrderCreated(response);
    }

    @After
    @Step("Отменить созданный в тесте заказ")
    public void cleanUpOrder() {
        if (track != null) {
            checkOk(orderClient.cancel(track), 200);
        }
    }
}
