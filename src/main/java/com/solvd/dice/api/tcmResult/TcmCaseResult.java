package com.solvd.dice.api.tcmResult;

public class TcmCaseResult {

    private int automationLaunchId;
    private int testExecutionId;
    private int statusId;
    private String trCaseId;
    private String tcmCaseId;

    public void setAutomationLaunchId(int automationLaunchId) {
        this.automationLaunchId = automationLaunchId;
    }

    public void setTestExecutionId(int testExecutionId) {
        this.testExecutionId = testExecutionId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setTcmCaseId(String tcmCaseId) {
        this.tcmCaseId = tcmCaseId;
    }

    public void setTrCaseId(String trCaseId) {
        this.trCaseId = trCaseId;
    }

    public int getTestExecutionId() {
        return testExecutionId;
    }

    public int getAutomationLaunchId() {
        return automationLaunchId;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getTcmCaseId() {
        return tcmCaseId;
    }

    public String getTrCaseId() {
        return trCaseId;
    }

    public int getStatusIdByName(String name){
        switch (name) {
            case "PASSED": return 247;
            case "FAILED": return 248;
            case "SKIPPED": return 249;
            case "RETEST": return 250;
            case "BLOCKED": return 251;
            case "INVALID": return 252;
        }
        return 0;
    }
}
