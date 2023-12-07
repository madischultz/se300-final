package com.se300.ledger.controller;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class LedgerRestExternalMockControllerTest {
    @Test
    void testGetAccountById() throws JSONException {

        String expectedJson = "{\"address\" : \"1\", \"balance\" : 46}";

        ExtractableResponse<Response> response = RestAssured
                .given()
                .filter(new RequestLoggingFilter())
                .auth().basic("sergey", "chapman")
                .contentType(ContentType.JSON)
                .when()
                .get("https://656e44f6bcc5618d3c24bc13.mockapi.io/accounts/1")
                .then()
                .statusCode(200)
                .extract();

        JSONAssert.assertEquals(expectedJson, response.body().asPrettyString(),true);
    }

    @Test
    public void testGetTransactionById() throws JSONException {

        //Transaction External Retrieval Test Method
        String expectedJson = "{\"amount\":77,\"fee\":24,\"note\":\"note 1\",\"payer\":\"interactive\",\"receiver\":\"Mandatory\",\"transactionId\":\"1\"}";

        ExtractableResponse<Response> response = RestAssured
                .given()
                .filter(new RequestLoggingFilter())
                .auth().basic("sergey", "chapman")
                .contentType(ContentType.JSON)
                .when()
                .get("https://656e44f6bcc5618d3c24bc13.mockapi.io/transactions/1")
                .then()
                .statusCode(200)
                .extract();

        JSONAssert.assertEquals(expectedJson, response.body().asPrettyString(),true);
    }
}
