package com.solvd.dice.api.dataSuite.Tabs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingsData {

    private final Settings settings;

    @JsonCreator
    public SettingsData(@JsonProperty("data") Settings settings) {
        this.settings = settings;
    }

    public Settings getSettings() {
        return settings;
    }
}
