package com.solvd.dice.api.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.solvd.dice.api.*;
import com.solvd.dice.api.dataSuite.launch.Result;
import com.solvd.dice.api.dataSuite.launch.Results;
import com.solvd.dice.api.dataSuite.testSuites.DataSuite;
import com.solvd.dice.api.dataSuite.testSuites.TestSuite;
import com.solvd.dice.api.tcmResult.TcmCaseResult;
import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.solvd.dice.api.tcmTestCasePojo.TestSuitePojo;
import com.zebrunner.carina.utils.R;
import io.restassured.response.ResponseBody;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class ApiTcm implements IAbstractTest {
    public static String token = "";
    public DataSuite dataSuite;
    List<TestSuite> suites;
    List<Result> result;

    @BeforeMethod
    public void createToken() {
        GetTokenMethod api = new GetTokenMethod();
        api.setToken(R.CONFIG.get("access_token"));
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 200, "Incorrect response.");
        ResponseBody body = api.callAPI().body();
        token = body.jsonPath().getString("authToken");
        Assert.assertNotNull(token, "Token missing.");
    }

    public void getAllTestCases() throws IOException {
        GetTestCasesMethod api = new GetTestCasesMethod();
        if (token == "") createToken();
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

    public void createTestCase(TestCasePojo testCase) {
        CreateTestCaseMethod api = new CreateTestCaseMethod();
        api.createTestCase(token, testCase);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 201, "Incorrect response");
    }

    public void createTestSuite(TestSuitePojo suite) {
        CreateTestSuiteMethod api = new CreateTestSuiteMethod();
        api.createTestSuite(token, suite);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 201, "Incorrect response");
    }

    public void getLaunchResults(int testRunId) throws IOException {
        GetTcmLaunchMethod api = new GetTcmLaunchMethod();
        if (token == "") createToken();
        api.getTcmLaunch(token, testRunId);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 200, "Incorrect response.");
        String bodyString = api.callAPI().body().asString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Results results = mapper.readValue(bodyString, Results.class);
        result = List.of(results.getResult());
    }

    public void setTcmStatus(String testRunId, List<String> groupedCases, TcmCaseResult tcmCaseResult) {
        SetTcmStatusMethod api = new SetTcmStatusMethod();
        if (token == "") createToken();
        api.setTcmStatus(token, testRunId, groupedCases, tcmCaseResult);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 204, "Incorrect response");
    }
}
