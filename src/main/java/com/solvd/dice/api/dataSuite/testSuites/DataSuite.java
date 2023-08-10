package com.solvd.dice.api.dataSuite.testSuites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataSuite {

    public final TestSuites test;

    @JsonCreator
    public DataSuite(@JsonProperty("data") TestSuites test) {
        this.test = test;
    }

    public TestSuites getTest() {
        return test;
    }
}
