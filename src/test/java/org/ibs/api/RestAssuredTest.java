package org.ibs.api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class RestAssuredTest {
    private static final String URI = "http://localhost:8080";
    @Test
    void test() {
        Specifications.installSpecifications(Specifications.requestSpec(URI),
                Specifications.responseSpec(200));

        given()
                .body(new GoodsData("Джекфрут", "FRUIT", true))
                .when()
                .post("api/food")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void deleteGood() {
        Specifications.installSpecifications(Specifications.requestSpec(URI),
                Specifications.responseSpec(200));
        given()
                .when()
                .post("api/data/reset")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void getGoods(){
        Specifications.installSpecifications(Specifications.requestSpec(URI),
                Specifications.responseSpec(200));
        given()
                .when()
                .get("/food")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);

    }
}
