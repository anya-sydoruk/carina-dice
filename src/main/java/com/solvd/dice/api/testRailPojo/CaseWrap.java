package com.solvd.dice.api.testRailPojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Case")
public final class CaseWrap {
    public final TestRailCase testRailCase;

    @JsonCreator
    public CaseWrap(@JsonProperty("Case") TestRailCase testRailCase) {
        this.testRailCase = testRailCase;
    }
}
