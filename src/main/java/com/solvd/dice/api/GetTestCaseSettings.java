package com.solvd.dice.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.http.HttpMethodType;

@Endpoint(url = "https://solvdinternal.zebrunner.com/api/tcm/v1/test-case-settings/fields-layout?projectId=42",
        methodType = HttpMethodType.GET)
public class GetTestCaseSettings extends AbstractApiMethodV2 {

    public void setToken(String token){
        setHeaders("Authorization=Bearer " + token);
    }

}
