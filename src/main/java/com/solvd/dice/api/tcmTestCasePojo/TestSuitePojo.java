package com.solvd.dice.api.tcmTestCasePojo;


public class TestSuitePojo {
    private String title;
    private String description;
    private int parentSuiteId;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setParentSuiteId(int parentSuiteId) {
        this.parentSuiteId = parentSuiteId;
    }

    public int getParentSuiteId() {
        return parentSuiteId;
    }
}
