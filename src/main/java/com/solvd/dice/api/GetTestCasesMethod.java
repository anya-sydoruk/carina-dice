package com.solvd.dice.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.http.HttpMethodType;

@Endpoint(url = "https://tcm.zebrunner.org/api/tcm/v1/test-repositories/main?projectId=100&includeCases=true&includeSubSuites=false",
methodType = HttpMethodType.GET)
public class GetTestCasesMethod extends AbstractApiMethodV2 {

    public void setToken(String token){
        setHeaders("Authorization=Bearer " + token);
    }
}
