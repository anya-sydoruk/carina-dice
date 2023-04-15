package com.solvd.dice.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;

@Endpoint(url = "https://tcm.zebrunner.org/api/tcm/v1/test-cases?projectId=100", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postCreateTestCase.json")
public class CreateTestCaseMethod extends AbstractApiMethodV2 {

    public void setToken(String token){
        setHeaders("Authorization=Bearer " + token);
    }
}
