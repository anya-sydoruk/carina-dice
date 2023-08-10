package com.solvd.dice.api.dataSuite.launch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestCases {

    public Long testExecutionId;
    public String tcmType;
    public String testCaseId;
    public String resultStatus;

    @JsonCreator
    public TestCases(@JsonProperty("testExecutionId") Long testExecutionId, @JsonProperty("tcmType") String tcmType,
                     @JsonProperty("testCaseId") String testCaseId, @JsonProperty("resultStatus") String resultStatus) {
        this.testExecutionId = testExecutionId;
        this.tcmType = tcmType;
        this.testCaseId = testCaseId;
        this.resultStatus = resultStatus;
    }

    public Long getTestExecutionId() {
        return testExecutionId;
    }

    public String getTcmType() {
        return tcmType;
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}
