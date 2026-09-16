package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.praktikum.scooter.client.OrderClient;

import static ru.praktikum.scooter.support.ResponseChecks.checkOrdersList;

public class ListOrdersTest {
    private static final int PAGE_LIMIT = 10;
    private static final int FIRST_PAGE = 0;
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Список заказов возвращается в массиве orders")
    public void shouldReturnOrdersList() {
        checkOrdersList(orderClient.list(PAGE_LIMIT, FIRST_PAGE), PAGE_LIMIT, FIRST_PAGE);
    }
}
