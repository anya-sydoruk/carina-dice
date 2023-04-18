package com.solvd.dice.api;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.CaseField;
import com.codepine.api.testrail.model.Project;
import com.codepine.api.testrail.model.Suite;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.solvd.dice.api.testRailPojo.CaseWrap;
import com.solvd.dice.api.testRailPojo.TestRailCase;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ApiTestRailTest {

    @Test()
    public void getTestCasesTest() throws IOException {
        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
                "akaravikou@solvd.com",
                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        Case testCase = testRail.cases().get(17, customCaseFields).execute();
        String stringCase = testCase.toString();
        System.out.println(stringCase);

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        CaseWrap caseWrap = mapper.readValue(stringCase, CaseWrap.class);

        //CaseWrap caseWrap = mapper.readValue(stringCase, CaseWrap.class);
        System.out.println(caseWrap);
    }
}
