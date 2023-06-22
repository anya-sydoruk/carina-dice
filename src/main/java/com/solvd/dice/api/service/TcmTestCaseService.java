package com.solvd.dice.api.service;

import java.util.ArrayList;

import com.codepine.api.testrail.model.Case;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.dice.api.tcmTestCasePojo.*;

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

    public TestCasePojo setTcmTestCaseTest(Case testCase, int tcmSuiteId) {
        TestCasePojo tcmTestCase = new TestCasePojo();
        tcmTestCase.setTestSuiteId(tcmSuiteId);
        tcmTestCase.setTitle(testCase.getTitle());

        Priority priority = new Priority();
        priority.setId(setPriority(testCase));

        AutomationState automationState = new AutomationState();
        automationState.setId(126);  // Not Automated - 124, To Be Automated - 125, Automated - 126

        RegularStep regularStep = new RegularStep();
        regularStep.setAction(testCase.getCustomField("steps"));
        regularStep.setExpectedResult(setNotNullLine(testCase.getCustomField("expected")));
        ArrayList<Object> stepAttachments = new ArrayList<>();
        regularStep.setAttachments(setNotNullArray(stepAttachments));

        Step step = new Step();
        step.setType("REGULAR");
        step.setRegularStep(regularStep);

        ArrayList<Step> steps = new ArrayList<>();
        steps.add(step);
        tcmTestCase.setSteps(steps);

        ArrayList<Object> attachments = new ArrayList<>();
        ArrayList<Object> customFields = new ArrayList<>();

        tcmTestCase.setPriority(priority);
        tcmTestCase.setAutomationState(automationState);
        tcmTestCase.setDescription("TestRail ID: " + testCase.getId() + ". Case type: " + TcmService.caseTypesMapTR.get(testCase.getTypeId()));
        tcmTestCase.setPreConditions(setNotNullLine(testCase.getCustomField("preconds")).replaceAll("\r", "\\\\n")
                .replaceAll("\n", "").replace("\"","\\\""));
        tcmTestCase.setPostConditions("");
        tcmTestCase.setAttachments(setNotNullArray(attachments));
        tcmTestCase.setCustomFields(setNotNullArray(customFields));

        ObjectMapper mapper = new ObjectMapper();
        try {
            stepsAsString = mapper.writeValueAsString(steps);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return tcmTestCase;
    }

    private String setNotNullLine(String line) {
        if (line != null)
            return line;
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
