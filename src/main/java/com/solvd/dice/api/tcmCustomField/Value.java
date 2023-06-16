package com.solvd.dice.api.tcmCustomField;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Value {
    private boolean hasIcon;
    private List<Option> options;

    public Value(){}

    @JsonCreator
    public Value(@JsonProperty("hasIcon") boolean hasIcon, @JsonProperty("options") List<Option> options){
        this.hasIcon = hasIcon;
        this.options = options;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public List<Option> getOptions() {
        return options;
    }
}
