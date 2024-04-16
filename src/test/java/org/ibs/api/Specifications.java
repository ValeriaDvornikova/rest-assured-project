package org.ibs.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {
    public static RequestSpecification requestSpec(String uri){
        return new RequestSpecBuilder().setContentType("application/json")
                .setBaseUri(uri).build();

    }
    public static ResponseSpecification responseSpec(int statusCode){
        return new ResponseSpecBuilder().expectStatusCode(statusCode).build();
    }
    public static void installSpecifications(RequestSpecification request, ResponseSpecification response){
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
