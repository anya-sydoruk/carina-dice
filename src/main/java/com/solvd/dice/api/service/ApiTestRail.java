package com.solvd.dice.api.service;

import java.util.List;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;

import org.testng.Assert;

public class ApiTestRail {

    private final String END_POINT = "https://modiusqa.testrail.net";
    private final String USERNAME = "akaravikou@solvd.com";
    private final String PASSWORD = "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/";

    public List<Case> getTestCases(int suiteId) {
        TestRail testRail = TestRail.builder(END_POINT, USERNAME, PASSWORD).build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        List<Case> allTitlesTR = testRail.cases().list(1, suiteId, customCaseFields).execute();
        Assert.assertFalse(allTitlesTR.isEmpty());
        return allTitlesTR;
    }

    public List<Suite> getSuites() {
        TestRail testRail = TestRail.builder(END_POINT, USERNAME, PASSWORD).build();

        List<Suite> suiteTitles = testRail.suites().list(1).execute();
        Assert.assertFalse(suiteTitles.isEmpty());
        return suiteTitles;
    }

    public List<Section> getSections(int suiteId) {
        TestRail testRail = TestRail.builder(END_POINT, USERNAME, PASSWORD).build();

        List<Section> sectionTitles = testRail.sections().list(1, suiteId).execute();
        Assert.assertFalse(sectionTitles.isEmpty());
        return sectionTitles;
    }
}
