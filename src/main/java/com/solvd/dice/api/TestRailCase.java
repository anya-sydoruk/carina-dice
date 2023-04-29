package com.solvd.dice.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestRailCase {

    public final TestRailTestCasePojo testRailTestCasePojo;

    @JsonCreator
    public TestRailCase(@JsonProperty("case")TestRailTestCasePojo testRailTestCasePojo) {
        this.testRailTestCasePojo = testRailTestCasePojo;
    }
}
