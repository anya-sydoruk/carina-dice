package com.solvd.dice.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TestCase {
    public final Integer id;
    public final String title;

    @JsonCreator
    public TestCase(@JsonProperty("id")Integer id, @JsonProperty("title")String title) {
        this.id = id;
        this.title = title;
    }
}
