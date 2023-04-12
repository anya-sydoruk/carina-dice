package com.solvd.dice.api;


import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.ContentType;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatus;
import com.zebrunner.carina.api.http.HttpResponseStatusType;


@Endpoint(url = "https://tcm.zebrunner.org/api/iam/v1/auth/login", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postToken.json")
public class GetTokenMethod extends AbstractApiMethodV2 {

}
