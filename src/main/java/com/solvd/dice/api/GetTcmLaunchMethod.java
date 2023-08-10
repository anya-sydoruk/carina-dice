package com.solvd.dice.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.utils.R;

@Endpoint(url = "${api_url}/api/reporting/api/tests/search?projectId=${project_id}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postTcmTestRunSearch.json")
public class GetTcmLaunchMethod extends AbstractApiMethodV2 {

    public void getTcmLaunch(String token, int testRunId) {
        replaceUrlPlaceholder("api_url", R.CONFIG.get("tcm_url"));
        replaceUrlPlaceholder("project_id", R.CONFIG.get("tcm_project_id"));
        setHeaders("Authorization=Bearer " + token);
        addProperty("testRunId", testRunId);
    }
}
