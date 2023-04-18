package com.solvd.dice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;


public class ApiTest implements IAbstractTest {
    public static String token = "";

    @BeforeMethod
    public void createToken() {
        GetTokenMethod api = new GetTokenMethod();
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 200, "Incorrect response.");
        ResponseBody body = api.callAPI().body();
        token = body.jsonPath().getString("authToken");
        Assert.assertNotNull(token, "Token missing.");
    }

    @Test()
    public void testGetAllTestCases() throws IOException {
        GetTestCasesMethod api = new GetTestCasesMethod();
        api.setToken(token);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 200, "Incorrect response.");
        String bodyString = api.callAPI().body().asString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DataSuite dataSuite = mapper.readValue(bodyString, DataSuite.class);
    }

    @Test()
    public void testCreateTestCase() {
        CreateTestCaseMethod api = new CreateTestCaseMethod();
        api.setToken(token);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 201, "Incorrect response");
    }
}
