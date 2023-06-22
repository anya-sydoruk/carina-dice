package com.solvd.dice.api.dataSuite.TestSuites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestCase {
    public final Long id;
    public final String title;
    public TestSuite parentSuite;
    public String path;

    @JsonCreator
    public TestCase(@JsonProperty("id") Long id, @JsonProperty("title") String title){
        this.id = id;
        this.title = title;
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

    @Override
    public String toString() {
        return "TestCase{" +
                "title='" + title + '\'' +
                '}';
    }
}
