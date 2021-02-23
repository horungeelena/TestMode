package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
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
        Faker faker = new Faker(new Locale("en"));
        Registration validActiveCustomer = new Registration(faker.name().firstName(),faker.internet().password(),"active");
        requestForm(validActiveCustomer);
        return validActiveCustomer;
    }

    public static Registration generateValidBlockedCustomer() {
        Faker faker = new Faker(new Locale("en"));
        Registration validBlockedCustomer = new Registration(faker.name().firstName(),faker.internet().password(),"blocked");
        requestForm(validBlockedCustomer);
        return validBlockedCustomer;
    }

    public static Registration generateInvalidLoginForCustomer() {
        Faker faker = new Faker(new Locale("en"));
        Registration invalidLogin = new Registration("invalidLogin",faker.internet().password(),"active");
        requestForm(invalidLogin);
        return invalidLogin;
    }

    public static Registration generateInvalidPasswordForCustomer() {
        Faker faker = new Faker(new Locale("en"));
        Registration invalidPassword = new Registration(faker.name().firstName(),"invalidPassword","active");
        requestForm(invalidPassword);
        return invalidPassword;
    }
}
