package com.solvd.dice.api.dataSuite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dice.api.dataSuite.TestSuite;

public final class TestSuites {
    public final TestSuite[] testSuites;

    @JsonCreator
    public TestSuites(@JsonProperty("testSuites") TestSuite[] testSuites) {
        this.testSuites = testSuites;
    }

    public TestSuite[] getTestSuites() {
        return testSuites;
    }
}
