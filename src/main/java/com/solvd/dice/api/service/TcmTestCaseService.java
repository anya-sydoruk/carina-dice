package com.solvd.dice.api.service;

import java.util.ArrayList;
import java.util.List;

import com.codepine.api.testrail.model.Case;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.dice.api.dataSuite.Tabs.Field;
import com.solvd.dice.api.tcmCustomField.Option;
import com.solvd.dice.api.tcmTestCasePojo.*;

public class TcmTestCaseService {

    public static String stepsAsString = "";
    public static String customFieldsAsString = "";

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
        regularStep.setExpectedResult(testCase.getCustomField("expected"));
        ArrayList<Object> stepAttachments = new ArrayList<>();
        regularStep.setAttachments(setNotNullArray(stepAttachments));

        Step step = new Step();
        step.setType("REGULAR");
        step.setRegularStep(regularStep);

        ArrayList<Step> steps = new ArrayList<>();
        steps.add(step);
        tcmTestCase.setSteps(steps);

        ArrayList<Object> attachments = new ArrayList<>();
        ArrayList<CustomFieldCase> customFields = new ArrayList<>();
        CustomFieldCase caseType = new CustomFieldCase();
        CustomFieldCase trId = new CustomFieldCase();
        for (Field fi : DataSuiteService.fields) {
            if (fi.getName().contains("Case Type")) {
                List<Option> options = fi.getValue().getOptions();
                for (Option op : options) {
                    if (op.getName().contains(TcmService.caseTypesMapTR.get(testCase.getTypeId()).getName())) {
                        caseType.setValue(op.getValue());
                        caseType.setId(fi.getId());
                        customFields.add(caseType);
                    }
                }
            }
            if (fi.getName().contains("TestRail ID")) {
                trId.setValue(String.valueOf(testCase.getId()));
                trId.setId(fi.getId());
                customFields.add(trId);
            }
        }

        tcmTestCase.setPriority(priority);
        tcmTestCase.setAutomationState(automationState);
        tcmTestCase.setDescription("");
        tcmTestCase.setPreConditions(setNotNullLine(testCase.getCustomField("preconds")));
        tcmTestCase.setPostConditions("");
        tcmTestCase.setAttachments(setNotNullArray(attachments));
        tcmTestCase.setCustomFields(customFields);

        ObjectMapper mapper = new ObjectMapper();
        try {
            stepsAsString = mapper.writeValueAsString(steps);
            customFieldsAsString = mapper.writeValueAsString(customFields);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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
