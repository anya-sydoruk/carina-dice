package com.solvd.dice.api.tcmCustomField;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Option {

    private String name;
    private boolean isDefault;
    private int relativePosition;
    private String value;

    public Option() {}

    @JsonCreator
    public Option(@JsonProperty("name") String name, @JsonProperty("isDefault") boolean isDefault, @JsonProperty("value") String value) {
        this.name = name;
        this.isDefault = isDefault;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    public String getName() {
        return name;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public int getRelativePosition() {
        return relativePosition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
