//package com.solvd.dice.api;
//
//import com.codepine.api.testrail.TestRail;
//import com.codepine.api.testrail.model.Case;
//import com.codepine.api.testrail.model.CaseField;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.solvd.dice.api.tcmTestCasePojo.AutomationState;
//import com.solvd.dice.api.tcmTestCasePojo.Priority;
//import com.solvd.dice.api.tcmTestCasePojo.RegularStep;
//import com.solvd.dice.api.tcmTestCasePojo.TestCase;
//import org.testng.annotations.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Experimental {
//
//    public TestRailTestCasePojo testRailTestCasePojo;
//
//    @Test()
//    public void getTestCaseTitleTest() throws IOException {
//        TestRail testRail = TestRail.builder("https://modiusqa.testrail.net",
//                "akaravikou@solvd.com",
//                "OsfdBz0Acymkokfg.yia-egy/c.NGEH1tiEx4s2L/").build();
//
//        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
//        Case testCase = testRail.cases().get(17, customCaseFields).execute();
//
//        TestCase tcmTestCase = new TestCase();
//        tcmTestCase.setTestSuiteId(3335);  //required field
//        tcmTestCase.setTitle(testCase.getTitle());
//
//        Priority priority = new Priority();
//        priority.setId(447);  //required field
//
//        RegularStep regularStep = new RegularStep();
//        regularStep.setAction(testCase.getCustomField("steps"));
//        regularStep.setExpectedResult(testCase.getCustomField("expected"));
//        tcmTestCase.setDescription("TestRail ID " + testCase.getId());
//
//        AutomationState automationState = new AutomationState();
//        automationState.setId(323);  //required field
//
//        ArrayList<Object> attachments = new ArrayList<>();
//        ArrayList<Object> customFields = new ArrayList<>();
//
//        tcmTestCase.setPriority(priority);
//        tcmTestCase.setAutomationState(automationState);
//        tcmTestCase.setAttachments(attachments);
//        tcmTestCase.setCustomFields(customFields);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(new File("/Users/akaravikou/Documents/carina/src/test/resources/api/post/postTcmTestCase.json"), tcmTestCase);
//    }
//}
