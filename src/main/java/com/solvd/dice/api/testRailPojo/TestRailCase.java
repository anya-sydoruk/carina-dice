package com.solvd.dice.api.testRailPojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TestRailCase {
    public final Long id;
    public final String title;
    public final Long suiteID;
    public final String customPreconditions;
    public final String customSteps;
    public final String customExpected;

    @JsonCreator
    public TestRailCase(@JsonProperty("id")Long id,@JsonProperty("title")String title,@JsonProperty("suite_id")Long suiteID,@JsonProperty("custom_preconds")String customPreconditions,@JsonProperty("custom_steps")String customSteps,@JsonProperty("custom_expected")String customExpected) {
        this.id = id;
        this.title = title;
        this.suiteID = suiteID;
        this.customPreconditions = customPreconditions;
        this.customSteps = customSteps;
        this.customExpected = customExpected;
    }
}
