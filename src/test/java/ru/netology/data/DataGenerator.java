package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

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

    public static Registration generateValidActiveCustomer() {
        Registration validActiveCustomer = new Registration(faker.name().firstName(),faker.internet().password(),"active");
        requestForm(validActiveCustomer);
        return validActiveCustomer;
    }

    public static Registration generateValidBlockedCustomer() {
        Registration validBlockedCustomer = new Registration(faker.name().firstName(),faker.internet().password(),"blocked");
        requestForm(validBlockedCustomer);
        return validBlockedCustomer;
    }

    public static Registration generateInvalidLoginForCustomer() {
        Registration invalidLogin = new Registration("логин",faker.internet().password(),"active");
        requestForm(invalidLogin);
        return new Registration(faker.name().firstName(),"пароль","active");
    }

    public static Registration generateInvalidPasswordForCustomer() {
        Registration invalidPassword = new Registration(faker.name().firstName(),"пароль","active");
        requestForm(invalidPassword);
        return new Registration("логин",faker.internet().password(),"active");
    }
}
