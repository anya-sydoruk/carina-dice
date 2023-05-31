package com.solvd.dice.api.dataSuite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TestSuite {
    public final Long id;
    public final String title;
    public final TestSuite[] childTestSuites;
    public final TestCase[] testCases;

    @JsonCreator
    public TestSuite(@JsonProperty("id") Long id, @JsonProperty("title") String title,
                     @JsonProperty("childTestSuites")TestSuite[] childTestSuites, @JsonProperty("childTestCases") TestCase[] testCases) {
        this.id = id;
        this.title = title;
        this.childTestSuites = childTestSuites;
        this.testCases = testCases;
    }
    public TestCase[] getTestCases() {
        return testCases;
    }

    public TestSuite[] getChildTestSuites() {
        return childTestSuites;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
