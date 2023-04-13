package com.solvd.dice.api;

import com.google.common.collect.Iterables;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.apache.commons.compress.utils.Lists;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

public class ApiTest implements IAbstractTest {
    public static String token = "";

    @Test()
    public void testCreateToken() {
        GetTokenMethod api = new GetTokenMethod();
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 200, "Incorrect response.");
        ResponseBody body = api.callAPI().body();
        token = body.jsonPath().getString("authToken");
        Assert.assertNotNull(token, "Token missing.");
    }

    @Test()
    public void testGetAllTestCases(){
        GetTestCasesMethod api = new GetTestCasesMethod();
        api.setToken(token);
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 200, "Incorrect response.");
        ResponseBody body = api.callAPI().body();

        JsonPath jsonPath = body.jsonPath().setRootPath("data.testSuites.childTestCases");
        List<Integer> id = jsonPath.get("id");

    }
}
