package com.solvd.dice.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataSuite {

    public final TestSuites test;

    @JsonCreator
    public DataSuite(@JsonProperty("data") TestSuites test) {
        this.test = test;
    }
}
