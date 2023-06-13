package com.solvd.dice.api.service;

import java.util.ArrayList;

import com.codepine.api.testrail.model.Case;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.dice.api.tcmTestCasePojo.AutomationState;
import com.solvd.dice.api.tcmTestCasePojo.Priority;
import com.solvd.dice.api.tcmTestCasePojo.RegularStep;
import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.solvd.dice.api.tcmTestCasePojo.Step;

public class TcmTestCaseService {

    public static String stepsAsString = "";

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

    public ArrayList<String> setSteps(String steps){
        ArrayList<String> expected2 = new ArrayList<>();
        String[] expected1 = steps.split("\r\n");
        for (String step : expected1) {
            step = step.replaceFirst("[0-9]\\)\\s","");
            expected2.add(step);
        }
        return expected2;
    }

    public TestCasePojo setTcmTestCaseTest(Case testCase, int tcmSuiteId ) {
        TestCasePojo tcmTestCase = new TestCasePojo();
        tcmTestCase.setTestSuiteId(tcmSuiteId);
        tcmTestCase.setTitle(testCase.getTitle());

        Priority priority = new Priority();
        priority.setId(setPriority(testCase));

        AutomationState automationState = new AutomationState();
        automationState.setId(126);  // Not Automated - 124, To Be Automated - 125, Automated - 126
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
            step.setRegularStep(regularStep);

            steps.add(step);
        }
        tcmTestCase.setSteps(steps);

 */
        RegularStep regularStep = new RegularStep();
        regularStep.setAction(testCase.getCustomField("steps"));
        regularStep.setExpectedResult(testCase.getCustomField("expected"));
        ArrayList<Object> stepAttachments = new ArrayList<>();
        regularStep.setAttachments(setNotNullArray(stepAttachments));

        Step step = new Step();
        step.setType("REGULAR");
        step.setRegularStep(regularStep);

        ArrayList<Step> steps = new ArrayList<>();
        steps.add(step);
        tcmTestCase.setSteps(steps);

        ObjectMapper mapper = new ObjectMapper();
        try {
            stepsAsString = mapper.writeValueAsString(steps);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        ArrayList<Object> attachments = new ArrayList<>();
        ArrayList<Object> customFields = new ArrayList<>();

        tcmTestCase.setPriority(priority);
        tcmTestCase.setAutomationState(automationState);
        tcmTestCase.setDescription("TestRail ID " + testCase.getId());
        tcmTestCase.setPreConditions(setNotNullLine(testCase.getCustomField("preconds")));
        tcmTestCase.setPostConditions("");
        tcmTestCase.setAttachments(setNotNullArray(attachments));
        tcmTestCase.setCustomFields(setNotNullArray(customFields));
        return tcmTestCase;
    }

    private String setNotNullLine(String line) {
        if (line != null)
            return line.replaceAll("\r", "\\\\n")
                    .replaceAll("\n", "").replace("\"","\\\"");
        else return "";
    }

    public ArrayList<Object> setNotNullArray(ArrayList<Object> array) {
        if (array != null) return array;
        else {
            ArrayList<Object> emptyArray = new ArrayList<>();
            emptyArray.add("");
            return emptyArray;
        }
    }
}
