package com.solvd.dice.api;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.solvd.dice.api.dataSuite.DataSuite;
import com.solvd.dice.api.dataSuite.TestSuite;
import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.solvd.dice.api.tcmTestCasePojo.TestSuitePojo;
import io.restassured.response.ResponseBody;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class ApiTest implements IAbstractTest {
    public static String token = "";
    public DataSuite dataSuite;
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

    public void testCreateTestCase(TestCasePojo testCase) {
        CreateTestCaseMethod api = new CreateTestCaseMethod();
        api.CreateTestCase(token, testCase);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 201, "Incorrect response");
    }

    public void testCreateTestSuite(TestSuitePojo suite) {
        CreateTestSuiteMethod api = new CreateTestSuiteMethod();
        api.CreateTestSuite(token, suite);
        if (suite.getParentSuiteId() != 0)
            api.addProperty("parentSuiteId", suite.getParentSuiteId());
        else api.addProperty("parentSuiteId", "");
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 201, "Incorrect response");
    }
}
