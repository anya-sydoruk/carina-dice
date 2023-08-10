package com.solvd.dice.api;

import java.util.List;

import com.solvd.dice.api.tcmResult.TcmCaseResult;
import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.utils.R;

@Endpoint(url = "${api_url}/api/tcm/v1/test-runs/${test_run}/test-case-results:batch?projectId=${project_id}", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postTcmStatus.json")
public class SetTcmStatusMethod extends AbstractApiMethodV2 {

    public void setTcmStatus(String token, String testRunId, List<String> groupedCases, TcmCaseResult tcmCaseResult) {
        replaceUrlPlaceholder("api_url", R.CONFIG.get("tcm_url"));
        replaceUrlPlaceholder("project_id", R.CONFIG.get("tcm_project_id"));
        replaceUrlPlaceholder("test_run", testRunId);
        setHeaders("Authorization=Bearer " + token);
        addProperty("testCaseIds", groupedCases);
        addProperty("statusId", tcmCaseResult.getStatusId());
        addProperty("api_url", R.CONFIG.get("tcm_url"));
        addProperty("project_id", R.CONFIG.get("tcm_project_id"));
        addProperty("automationLaunchId", tcmCaseResult.getAutomationLaunchId());
        addProperty("automationExecutionId", tcmCaseResult.getTestExecutionId());
    }
}
