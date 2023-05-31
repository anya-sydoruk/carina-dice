package com.solvd.dice.api.dataSuite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TestCase {
    public final Long id;
    public final String title;

    @JsonCreator
    public TestCase(@JsonProperty("id")Long id, @JsonProperty("title")String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "title='" + title + '\'' +
                '}';
    }
}
