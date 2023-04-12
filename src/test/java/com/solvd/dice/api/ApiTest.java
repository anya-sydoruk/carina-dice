package com.solvd.dice.api;

import com.qaprosoft.carina.core.foundation.IAbstractTest;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTest implements IAbstractTest {

    @Test()
    public void testCreateToken() {
        GetTokenMethod api = new GetTokenMethod();
        int code = api.callAPI().getStatusCode();
        Assert.assertEquals(code, 200, "Incorrect response.");
        ResponseBody body = api.callAPI().body();
        String token = body.jsonPath().getString("authToken");
        Assert.assertNotNull(token, "Token missing.");
    }
}
