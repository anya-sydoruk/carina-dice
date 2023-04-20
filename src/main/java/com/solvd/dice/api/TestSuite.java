package com.solvd.dice.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TestSuite {
    public final Long id;
    public final String title;
    public final TestCase[] testCases;

    @JsonCreator
    public TestSuite(@JsonProperty("id") Long id,@JsonProperty("title") String title,@JsonProperty("childTestCases") TestCase[] testCases) {
        this.id = id;
        this.title = title;
        this.testCases = testCases;
    }

    public TestCase getTestCases() {
        return testCases;
    }
}
