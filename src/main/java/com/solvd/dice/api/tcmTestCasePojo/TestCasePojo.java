package com.solvd.dice.api.tcmTestCasePojo;

import java.util.ArrayList;

public class TestCasePojo {

    private String title;
    private int testSuiteId;
    private Priority priority;
    private AutomationState automationState;
    private String description;
    private boolean draft;
    private boolean deprecated;
    private String preConditions;
    private String postConditions;
    private ArrayList<Step> steps;
    private ArrayList<Object> attachments;
    private ArrayList<CustomFieldCase> customFields;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTestSuiteId() {
        return testSuiteId;
    }

    public void setTestSuiteId(int testSuiteId) {
        this.testSuiteId = testSuiteId;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public AutomationState getAutomationState() {
        return automationState;
    }

    public void setAutomationState(AutomationState automationState) {
        this.automationState = automationState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public String getPreConditions() {
        return preConditions;
    }

    public void setPreConditions(String preConditions) {
        this.preConditions = preConditions;
    }

    public String getPostConditions() {
        return postConditions;
    }

    public void setPostConditions(String postConditions) {
        this.postConditions = postConditions;
    }

    public void setSteps(ArrayList<Step> steps) {this.steps = steps;}

    public ArrayList<Step> getSteps() {return steps;}

    public ArrayList<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Object> attachments) {
        this.attachments = attachments;
    }

    public ArrayList<CustomFieldCase> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(ArrayList<CustomFieldCase> customFields) {
        this.customFields = customFields;
    }
}
