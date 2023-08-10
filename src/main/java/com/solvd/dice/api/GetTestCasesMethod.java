package com.solvd.dice.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.utils.R;

@Endpoint(url = "${api_url}/api/tcm/v1/test-repositories/main?projectId=${project_id}",
methodType = HttpMethodType.GET)
public class GetTestCasesMethod extends AbstractApiMethodV2 {

    public void setToken(String token){
        replaceUrlPlaceholder("api_url", R.CONFIG.get("tcm_url"));
        replaceUrlPlaceholder("project_id", R.CONFIG.get("tcm_project_id"));
        setHeaders("Authorization=Bearer " + token);
    }
}
