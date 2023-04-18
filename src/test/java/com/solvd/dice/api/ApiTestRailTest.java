package com.solvd.dice.api;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.CaseField;
import com.codepine.api.testrail.model.Project;
import com.codepine.api.testrail.model.Suite;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.util.List;

public class ApiTestRailTest {

    @Test()
    public void getTestCasesTest(){
        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
                "akaravikou@solvd.com",
                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        Case testCase = testRail.cases().get(17, customCaseFields).execute();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DataSuite dataSuite = mapper.readValue(, DataSuite.class);
        System.out.println(testCase);
    }
}
