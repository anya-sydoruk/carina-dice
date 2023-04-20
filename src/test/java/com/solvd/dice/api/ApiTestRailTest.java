package com.solvd.dice.api;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ApiTestRailTest {

    @Test()
    public void getTestCaseTitleTest() throws IOException {
        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
                "akaravikou@solvd.com",
                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        Case testCase = testRail.cases().get(17, customCaseFields).execute();
        String testCaseTitle = testCase.getTitle();
        Assert.assertNotNull(testCaseTitle, "Title is absent");
    }
}
