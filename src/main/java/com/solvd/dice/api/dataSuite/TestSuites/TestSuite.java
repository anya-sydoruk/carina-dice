package com.solvd.dice.api.dataSuite.TestSuites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TestSuite {
    public final Long id;
    public final String title;
    public final TestSuite[] childTestSuites;
    public final TestCase[] childTestCases;

    @JsonCreator
    public TestSuite(@JsonProperty("id") Long id, @JsonProperty("title") String title,
                     @JsonProperty("childTestSuites")TestSuite[] childTestSuites, @JsonProperty("childTestCases") TestCase[] childTestCases) {
        this.id = id;
        this.title = title;
        this.childTestSuites = childTestSuites;
        this.childTestCases = childTestCases;
    }
    public TestCase[] getTestCases() {
        return childTestCases;
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
