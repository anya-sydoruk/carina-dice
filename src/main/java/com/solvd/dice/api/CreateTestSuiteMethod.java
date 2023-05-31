package com.solvd.dice.api;

import com.solvd.dice.api.tcmTestCasePojo.TestSuitePojo;
import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;

@Endpoint(url = "https://solvdinternal.zebrunner.com/api/tcm/v1/test-suites?projectId=42", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postTcmTestSuite.json")
public class CreateTestSuiteMethod extends AbstractApiMethodV2 {

    public void CreateTestSuite(String token, TestSuitePojo suite) {
        setHeaders("Authorization=Bearer " + token);
        addProperty("title", suite.getTitle());
        addProperty("description", suite.getDescription());
    }
}

