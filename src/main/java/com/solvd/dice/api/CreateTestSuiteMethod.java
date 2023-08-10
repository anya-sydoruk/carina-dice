package com.solvd.dice.api;

import com.solvd.dice.api.tcmTestCasePojo.TestSuitePojo;
import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.utils.R;

@Endpoint(url = "${api_url}/api/tcm/v1/test-suites?projectId=${project_id}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postTcmTestSuite.json")
public class CreateTestSuiteMethod extends AbstractApiMethodV2 {

    public void createTestSuite(String token, TestSuitePojo suite) {
        replaceUrlPlaceholder("api_url", R.CONFIG.get("tcm_url"));
        replaceUrlPlaceholder("project_id", R.CONFIG.get("tcm_project_id"));
        setHeaders("Authorization=Bearer " + token);
        addProperty("title", suite.getTitle());
        addProperty("description", suite.getDescription());
        if (suite.getParentSuiteId() != 0)
            addProperty("parentSuiteId", suite.getParentSuiteId());
        else addProperty("parentSuiteId", "");
    }
}

