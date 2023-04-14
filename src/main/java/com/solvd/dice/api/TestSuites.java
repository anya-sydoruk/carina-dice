package com.solvd.dice.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TestSuites {
    public final TestSuite[] testSuites;

    @JsonCreator
    public TestSuites(@JsonProperty("testSuites") TestSuite[] testSuites) {
        this.testSuites = testSuites;
    }
}
