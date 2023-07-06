package com.solvd.dice.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;

@Endpoint(url = "https://solvdinternal.zebrunner.com/api/reporting/api/tests/search?projectId=42", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postTcmTestRunSearch.json")
public class GetTcmLaunchMethod extends AbstractApiMethodV2 {
}
