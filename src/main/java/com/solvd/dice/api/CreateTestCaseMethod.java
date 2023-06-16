package com.solvd.dice.api;

import java.util.ArrayList;

import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;

import static com.solvd.dice.api.service.TcmTestCaseService.customFieldsAsString;
import static com.solvd.dice.api.service.TcmTestCaseService.stepsAsString;

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
        if (testCase.getSteps().get(0).getRegularStep().getAction() != null)
            addProperty("steps", stepsAsString);
        else {
            ArrayList<Object> emptyArray = new ArrayList<>();
            emptyArray.add("");
            addProperty("steps", emptyArray);
        }
        addProperty("attachments", testCase.getAttachments());
        addProperty("customFields", customFieldsAsString);
    }
}
