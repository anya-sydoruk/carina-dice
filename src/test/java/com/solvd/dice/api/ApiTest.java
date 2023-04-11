package com.solvd.dice.api;

import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import org.testng.annotations.Test;

public class ApiTest implements IAbstractTest {

    @Test()
    public void testCreateToken() {
        GetTokenMethod api = new GetTokenMethod();
        api.expectResponseStatus(HttpResponseStatusType.OK_200);
        api.callAPI();
        api.validateResponse();
    }

}
