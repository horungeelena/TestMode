package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Registration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;

public class RegistrationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void activeValidCustomer() {
        Registration customer = generateValidActiveCustomer();
        $("[data-test-id=login] input").setValue(customer.getLogin());
        $("[data-test-id=password] input").setValue(customer.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Личный кабинет")).waitUntil(Condition.visible,15000);
    }

    @Test
    void blockedValidCustomer() {
        Registration customer = generateValidBlockedCustomer();
        $("[data-test-id=login] input").setValue(customer.getLogin());
        $("[data-test-id=password] input").setValue(customer.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void invalidLoginActiveForCustomer() {
        Registration customer = generateInvalidLoginForCustomer();
        $("[data-test-id=login] input").setValue(customer.getLogin());
        $("[data-test-id=password] input").setValue(customer.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).waitUntil(Condition.visible,15000);
    }

    @Test
    void invalidPasswordActiveForCustomer() {
        Registration customer = generateInvalidPasswordForCustomer();
        $("[data-test-id=login] input").setValue(customer.getLogin());
        $("[data-test-id=password] input").setValue(customer.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).waitUntil(Condition.visible,15000);
    }
}