package com.solvd.dice.api;

import java.util.List;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTestRailTest {

    private final String END_POINT = "https://modiusqa.testrail.net";
    private final String USERNAME = "akaravikou@solvd.com";
    private final String PASSWORD = "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/";

    @Test()
    public Case getTestCaseTest(){
        TestRail testRail = TestRail.builder(END_POINT, USERNAME, PASSWORD).build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        Case testCase = testRail.cases().get(17, customCaseFields).execute();
        return testCase;
    }

    @Test()
    public List<Case> getTestCasesTest(int suiteId){
        TestRail testRail = TestRail.builder(END_POINT, USERNAME, PASSWORD).build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        List<Case> allTitlesTR = testRail.cases().list(1, suiteId, customCaseFields).execute();
        Assert.assertFalse(allTitlesTR.isEmpty());
        return allTitlesTR;
    }

    @Test()
    public List<Suite> getSuitesTest(){
        TestRail testRail = TestRail.builder(END_POINT, USERNAME, PASSWORD).build();

        List<Suite> suiteTitles = testRail.suites().list(1).execute();
        Assert.assertFalse(suiteTitles.isEmpty());
        return suiteTitles;
    }

    @Test()
    public List<Section> getSectionsTest(int suiteId){
        TestRail testRail = TestRail.builder(END_POINT, USERNAME, PASSWORD).build();

        List<Section> sectionTitles = testRail.sections().list(1, suiteId).execute();
        Assert.assertFalse(sectionTitles.isEmpty());
        return sectionTitles;
    }
}
