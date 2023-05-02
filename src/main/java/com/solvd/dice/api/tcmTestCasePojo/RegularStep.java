package com.solvd.dice.api.tcmTestCasePojo;

import java.util.ArrayList;

public class RegularStep {

    private String action;
    private String expectedResult;
    private ArrayList<Object> attachments;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public ArrayList<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Object> attachments) {
        this.attachments = attachments;
    }
}
