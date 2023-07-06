package com.solvd.dice.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;

@Endpoint(url = "https://solvdinternal.zebrunner.com/api/tcm/v1/test-runs/63/test-case-results:batch?projectId=42", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postTcmStatus.json")
public class SetTcmStatusMethod extends AbstractApiMethodV2 {
}
