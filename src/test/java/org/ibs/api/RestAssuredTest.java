package org.ibs.api;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {
    private static final String URI = "http://localhost:8080";
    private static final int INIT_SIZE = 4;
    private final Map<String, String> withoutContentHeaders = Map.of("connection", "keep-alive", "content-length", "0");

    @BeforeEach
    void setUp() {
        Specifications.installSpecifications(Specifications.requestSpec(URI),
                Specifications.responseSpec(HttpStatus.SC_OK));
    }
    @Test
    void addProductTest() {
        GoodsData sendData = new GoodsData("Джекфрут", "FRUIT", true);

        given()
                .body(sendData)
                .header("Content-Type", "application/json")
                .when()
                .post("/api/food")
                .then().log().all()
                .assertThat()
                .headers(withoutContentHeaders);
    }

    @Test
    public void deleteProductTest() {
        given()
                .when()
                .post("/api/data/reset")
                .then()
                .log().all()
                .assertThat()
                .headers(withoutContentHeaders);
    }

    @Test
    public void getProductsTest(){
        given()
                .when()
                .get("/api/food")
                .then()
                .log().all()
                .assertThat()
                .header("Content-Type", "application/json")
                .header("transfer-encoding", "chunked")
                .body(notNullValue())
                .and()
                .body("", allOf(
                        hasSize(INIT_SIZE),
                        everyItem(notNullValue())
                ));
    }
    @AfterEach
    public void after() {
        deleteProductTest(); // удаление добавленые товары и приводит список к исходному
    }
}
