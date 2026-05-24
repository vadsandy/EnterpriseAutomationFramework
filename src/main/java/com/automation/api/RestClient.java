package com.automation.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestClient {
    private RequestSpecification request;

    // Constructor initializes the request with the base URI and the Allure auto-logger
    public RestClient(String baseUri) {
        request = RestAssured.given()
                .filter(new AllureRestAssured()) // Magic line: Auto-logs everything to the HTML report!
                .baseUri(baseUri)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    // Dynamic configuration methods
    public void addHeaders(Map<String, String> headers) {
        request.headers(headers);
    }

    public void addQueryParams(Map<String, String> queryParams) {
        request.queryParams(queryParams);
    }

    public void setBody(Object payload) {
        request.body(payload);
    }

    public void setBasicAuth(String username, String password) {
        request.auth().preemptive().basic(username, password);
    }

    // Reusable HTTP Methods
    public Response get(String endpoint) {
        return request.get(endpoint);
    }

    public Response post(String endpoint) {
        return request.post(endpoint);
    }

    public Response put(String endpoint) {
        return request.put(endpoint);
    }

    public Response delete(String endpoint) {
        return request.delete(endpoint);
    }
}