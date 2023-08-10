package com.solvd.dice.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.utils.R;

@Endpoint(url = "${api_url}/api/iam/v1/auth/refresh/", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postToken.json")
public class GetTokenMethod extends AbstractApiMethodV2 {

    public void setToken(String token) {
        replaceUrlPlaceholder("api_url", R.CONFIG.get("tcm_url"));
        addProperty("access_token", token);
    }
}
