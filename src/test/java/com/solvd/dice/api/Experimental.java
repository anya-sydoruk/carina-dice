package com.solvd.dice.api;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.CaseField;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class Experimental {

    public TestRailTestCasePojo testRailTestCasePojo;

    @Test()
    public void getTestCaseTitleTest() throws IOException {
        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
                "akaravikou@solvd.com",
                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        Case testCase = testRail.cases().get(17, customCaseFields).execute();
        TestRailTestCasePojo testRailTestCasePojo = new TestRailTestCasePojo();
        testRailTestCasePojo.setId((long) testCase.getId());
        testRailTestCasePojo.setTitle(testCase.getTitle());
        testRailTestCasePojo.setSection_id(testCase.getSectionId());
        //template_id
        testRailTestCasePojo.setType_id(testCase.getTypeId());
        testRailTestCasePojo.setPriority_id(testCase.getPriorityId());
        //testRailTestCasePojo.setMilestone_id(testCase.getMilestoneId());
        testRailTestCasePojo.setRefs(testCase.getRefs());
        testRailTestCasePojo.setCreated_by(testCase.getCreatedBy());
        testRailTestCasePojo.setCreated_on(testCase.getCreatedOn());
        testRailTestCasePojo.setUpdated_by(testCase.getUpdatedBy());
        testRailTestCasePojo.setUpdated_on(testCase.getUpdatedOn());
        testRailTestCasePojo.setEstimate(testCase.getEstimate());
        testRailTestCasePojo.setEstimate_forecast(testCase.getEstimateForecast());
        testRailTestCasePojo.setSuite_id(testCase.getSuiteId());
    }
}
