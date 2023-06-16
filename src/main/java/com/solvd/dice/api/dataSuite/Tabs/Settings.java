package com.solvd.dice.api.dataSuite.Tabs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Settings {

    public final Tab[] tabs;
    private final Field[] fields;

    @JsonCreator
    public Settings(@JsonProperty("tabs") Tab[] tabs, @JsonProperty("fields") Field[] fields) {
        this.tabs = tabs;
        this.fields = fields;
    }

    public Tab[] getTabs() {
        return tabs;
    }

    public Field[] getFields() {return fields;}
}
