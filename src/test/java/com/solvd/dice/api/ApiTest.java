package com.solvd.dice.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.List;


public class ApiTest implements IAbstractTest {
    public static String token = "";
    public com.solvd.dice.api.DataSuite dataSuite;
    List<TestSuite> suites;

    @BeforeMethod
    public void createToken() {
        GetTokenMethod api = new GetTokenMethod();
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 200, "Incorrect response.");
        ResponseBody body = api.callAPI().body();
        token = body.jsonPath().getString("authToken");
        Assert.assertNotNull(token, "Token missing.");
    }

    public void testGetAllTestCases() throws IOException {
        GetTestCasesMethod api = new GetTestCasesMethod();
        api.setToken(token);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 200, "Incorrect response.");
        String bodyString = api.callAPI().body().asString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        dataSuite = mapper.readValue(bodyString, DataSuite.class);

        suites = List.of(dataSuite.getTest().getTestSuites());
    }

    public void testCreateTestCase() {
        CreateTestCaseMethod api = new CreateTestCaseMethod();
        api.setToken(token);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 201, "Incorrect response");
    }

    public void testCreateTestSuite() {
        CreateTestSuiteMethod api = new CreateTestSuiteMethod();
        api.setToken(token);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 201, "Incorrect response");
    }
}
