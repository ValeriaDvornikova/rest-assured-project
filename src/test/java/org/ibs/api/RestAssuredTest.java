package org.ibs.api;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RestAssuredTest {
    private static final String URI = "http://localhost:8080";
    private static final int INIT_SIZE = 4;
    private final Map<String, String> withoutContentHeaders = Map.of("connection", "keep-alive", "content-length", "0");

    private static Map<String,String> cookies;
    @BeforeEach
    void setUp() {
        Specifications.installSpecifications(Specifications.requestSpec(URI),
                Specifications.responseSpec(HttpStatus.SC_OK));
    }
    @Test
    void addProductTest() {
        GoodsData sendData = new GoodsData("Джекфрут", "FRUIT", true);

        cookies = given()
                .body(sendData)
                .header("Content-Type", "application/json")
                .when()
                .post("/api/food")
                .then().log().all()
                .assertThat()
                .headers(withoutContentHeaders)
                .extract()
                .cookies();

        List<GoodsData> afterPostList = given()
                .when()
                .cookies(cookies)
                .get("/api/food")
                .then()
                .log().all()
                .extract()
                .body()
                .jsonPath()
                .getList("", GoodsData.class);

        assertAll(
                () -> assertThat(afterPostList, hasSize(INIT_SIZE + 1)),
                () -> assertThat(afterPostList, hasItem(allOf(
                        hasProperty("name", equalTo(sendData.getName())),
                        hasProperty("type", equalTo(sendData.getType())),
                        hasProperty("exotic", equalTo(sendData.getExotic())
                ))))
        );
    }

    @Test
    public void deleteProductTest() {
        given()
                .when()
                .post("/api/data/reset")
                .then()
                .log().all()
                .assertThat();
                //.headers(withoutContentHeaders);
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
