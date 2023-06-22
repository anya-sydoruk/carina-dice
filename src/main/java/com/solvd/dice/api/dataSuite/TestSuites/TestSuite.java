package com.solvd.dice.api.dataSuite.TestSuites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestSuite {
    public final Long id;
    public final String title;
    public final TestSuite[] childTestSuites;
    public final TestCase[] childTestCases;
    public TestSuite parentSuite;
    public String path;

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

    public void setParentSuite(TestSuite parentSuite) {
        this.parentSuite = parentSuite;
    }

    public TestSuite getParentSuite() {
        return parentSuite;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
