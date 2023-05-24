package com.solvd.dice.api.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codepine.api.testrail.model.Case;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.dice.api.tcmTestCasePojo.AutomationState;
import com.solvd.dice.api.tcmTestCasePojo.Priority;
import com.solvd.dice.api.tcmTestCasePojo.RegularStep;
import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.solvd.dice.api.tcmTestCasePojo.Step;

public class TcmTestCaseSetup {

    public int setPriority(Case testCase){
        int priorityTestRail = testCase.getPriorityId();
        switch(priorityTestRail) {
            case 1: //Low
                return 171;
            case 2: //Medium
                return 170;
            case 3: //High
            case 4: //Critical
                return 169;
            default:
        }
        return 172; //Trivial
    }

    public int setAutomationState(Case testCase){
        int priorityTestRail = testCase.getCustomField("automation_type");
        switch(priorityTestRail) {
            case 0:
                return 124; //Not Automated - 124, To be - 125, Automated - 126
        }
        return 124;
    }

    public ArrayList<String> setSteps(String steps){
        ArrayList<String> expected2 = new ArrayList<>();
        String[] expected1 = steps.split("\r\n");
        for (String step : expected1) {
            step = step.replaceFirst("[0-9]\\)\\s","");
            expected2.add(step);
        }
        System.out.println(expected2);
        return expected2;
    }

    public TestCasePojo setTcmTestCaseTest(Case testCase, int tcmSuiteId ){
        TestCasePojo tcmTestCase = new TestCasePojo();
        tcmTestCase.setTestSuiteId(tcmSuiteId);
        tcmTestCase.setTitle(testCase.getTitle());

        Priority priority = new Priority();
        priority.setId(setPriority(testCase));

        AutomationState automationState = new AutomationState();
        automationState.setId(124);  //required field
/*
        ArrayList<String> stepsArray = setSteps(testCase.getCustomField("steps"));
        ArrayList<String> expectedArray = setSteps(testCase.getCustomField("expected"));
        ArrayList<Step> steps = new ArrayList<>();

        for (String stepOne : stepsArray) {
            RegularStep regularStep = new RegularStep();
            regularStep.setAction(stepOne);
            regularStep.setExpectedResult(expectedArray.get(stepsArray.indexOf(stepOne)));

            Step step = new Step();
            step.setType("REGULAR");
            step.setRelativePosition(stepsArray.indexOf(stepOne));
            step.setRegularStep(regularStep);

            steps.add(step);
        }
        tcmTestCase.setSteps(steps);
 */
        RegularStep regularStep = new RegularStep();
        if (testCase.getCustomField("steps")!=null)
        regularStep.setAction(testCase.getCustomField("steps"));
        else regularStep.setAction("");
        if (testCase.getCustomField("expected")!=null)
            regularStep.setExpectedResult(testCase.getCustomField("expected"));
        else regularStep.setExpectedResult("");

        Step step = new Step();
        step.setType("REGULAR");
        step.setRelativePosition(0);
        step.setRegularStep(regularStep);

        ArrayList<Step> steps = new ArrayList<>();
        steps.add(step);
        tcmTestCase.setSteps(steps);

        ArrayList<Object> attachments = new ArrayList<>();
        ArrayList<Object> customFields = new ArrayList<>();

        tcmTestCase.setPriority(priority);
        tcmTestCase.setAutomationState(automationState);
        tcmTestCase.setDescription("TestRail ID " + testCase.getId());
        tcmTestCase.setPreConditions(testCase.getCustomField("preconds"));
        tcmTestCase.setAttachments(attachments);
        tcmTestCase.setCustomFields(customFields);
        return tcmTestCase;
    }

    public void createTestCaseFile(ArrayList<TestCasePojo> tcmTestCase) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/api/post/postTcmTestCase.json"), tcmTestCase);
    }

}
