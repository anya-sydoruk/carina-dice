package com.solvd.dice.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;

@Endpoint(url = "https://solvdinternal.zebrunner.com/api/iam/v1/auth/refresh/", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postToken.json")
public class GetTokenMethod extends AbstractApiMethodV2 {
    public void setToken(String token) { addProperty("access_token", token);}
}
