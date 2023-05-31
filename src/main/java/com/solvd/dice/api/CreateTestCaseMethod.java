package com.solvd.dice.api;

import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;

@Endpoint(url = "https://solvdinternal.zebrunner.com/api/tcm/v1/test-cases?projectId=42", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postTcmTestCase.json")
public class CreateTestCaseMethod extends AbstractApiMethodV2 {

    public void CreateTestCase(String token, TestCasePojo testCase) {
        setHeaders("Authorization=Bearer " + token);
        addProperty("title", testCase.getTitle());
        addProperty("testSuiteId", testCase.getTestSuiteId());
        addProperty("priority_id", testCase.getPriority().getId());
        addProperty("automationState_id", testCase.getAutomationState().getId());
        addProperty("description", testCase.getDescription());
        addProperty("preConditions", testCase.getPreConditions());
        addProperty("postConditions", testCase.getPostConditions());
        addProperty("action", testCase.getSteps().get(0).getRegularStep().getAction());
        addProperty("expectedResult", testCase.getSteps().get(0).getRegularStep().getExpectedResult());
        //addProperty("attachments_step", testCase.getSteps().get(0).getRegularStep().getAttachments());
        //addProperty("attachments", testCase.getAttachments());
        //addProperty("customFields", testCase.getCustomFields());
    }
}
