package com.solvd.dice.api.dataSuite.launch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

    public TestCases[] testCases;
    public String status;

    @JsonCreator
    public Result(@JsonProperty("testCases") TestCases[] testCases, @JsonProperty("status") String status) {
        this.testCases = testCases;
        this.status = status;
    }

    public TestCases[] getTestCases() {
        return testCases;
    }

    public String getStatus() {
        return status;
    }
}
