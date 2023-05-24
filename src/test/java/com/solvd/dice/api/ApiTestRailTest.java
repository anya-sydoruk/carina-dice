package com.solvd.dice.api;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ApiTestRailTest {

    public String testCaseTitle;

    @Test()
    public void getTestCaseTitleTest() throws IOException {
        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
                "akaravikou@solvd.com",
                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        Case testCase = testRail.cases().get(17, customCaseFields).execute();
        testCaseTitle = testCase.getTitle();
        Assert.assertNotNull(testCaseTitle, "Title is absent");
    }

    @Test()
    public List<Case> getTestCasesTest(int suiteId) throws IOException {
        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
                "akaravikou@solvd.com",
                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        List<Case> allTitlesTR = testRail.cases().list(1, suiteId, customCaseFields).execute();
        Assert.assertFalse(allTitlesTR.isEmpty());
        return allTitlesTR;
    }

    @Test()
    public Case getTestCaseTest() throws IOException {
        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
                "akaravikou@solvd.com",
                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        Case testCase = testRail.cases().get(17, customCaseFields).execute();
        return testCase;
    }

    @Test()
    public List<Suite> getSuitesTest() throws IOException {
        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
                "akaravikou@solvd.com",
                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();

        List<Suite> suiteTitles = testRail.suites().list(1).execute();
        Assert.assertFalse(suiteTitles.isEmpty());
        return suiteTitles;
    }

    @Test()
    public List<Section> getSectionsTest(int suiteId) throws IOException {
        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
                "akaravikou@solvd.com",
                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();

        List<Section> sectionTitles = testRail.sections().list(1, suiteId).execute();
        Assert.assertFalse(sectionTitles.isEmpty());
        return sectionTitles;
    }
}
