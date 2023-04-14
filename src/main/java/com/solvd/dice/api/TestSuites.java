package com.solvd.dice.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TestSuites {
    public final TestSuite[] testSuites;

    @JsonCreator
    public TestSuites(@JsonProperty("testSuites") TestSuite[] testSuites) {
        this.testSuites = testSuites;
    }

    public static final class TestSuite {
        public final TestCase[] testCases;

        @JsonCreator
        public TestSuite(@JsonProperty("childTestCases") TestCase[] testCases) {
            this.testCases = testCases;
        }

        public static final class TestCase {
            public final Integer id;
            public final String title;

            @JsonCreator
            public TestCase(@JsonProperty("id") Integer id, @JsonProperty("title") String title) {
                this.id = id;
                this.title = title;
            }
        }
    }
    public TestSuite[] getTestSuites() {
        return testSuites;
    }
}