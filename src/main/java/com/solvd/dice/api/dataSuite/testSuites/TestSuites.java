package com.solvd.dice.api.dataSuite.testSuites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
