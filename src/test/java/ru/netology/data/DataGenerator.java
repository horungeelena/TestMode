package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    static Faker faker = new Faker(new Locale("en"));

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void requestForm(Registration registration) {
        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static Registration generateCustomer(String status) {
        Registration customer = new Registration(faker.name().firstName(),faker.internet().password(),status);
        requestForm(customer);
        return customer;
    }

    public static Registration generateInvalidLoginForCustomer() {
        val password = faker.internet().password();
        val invalidLogin = new Registration(faker.name().firstName(), password, "active");
        requestForm(invalidLogin);
        return new Registration(faker.name().firstName(), password, "active");
    }

    public static Registration generateInvalidPasswordForCustomer() {
        val login = faker.name().firstName();
        val invalidPassword = new Registration(login, faker.internet().password(), "active");
        requestForm(invalidPassword);
        return new Registration(login, faker.internet().password(), "active");
    }

}
