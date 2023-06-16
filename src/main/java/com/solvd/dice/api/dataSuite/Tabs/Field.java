package com.solvd.dice.api.dataSuite.Tabs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.dice.api.tcmCustomField.Value;

public final class Field {

    private final int id;
    private final String type;
    private final int tabId;
    private final String name;
    private final String dataType;
    private final Value value;

    @JsonCreator
    public Field(@JsonProperty("id") int id, @JsonProperty("type") String type, @JsonProperty("tabId") int tabId,
                 @JsonProperty("name") String name, @JsonProperty("dataType") String dataType, @JsonProperty("value") Value value) {
        this.id = id;
        this.type = type;
        this.tabId = tabId;
        this.name = name;
        this.dataType = dataType;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public int getTabId() {
        return tabId;
    }

    public String getType() {
        return type;
    }

    public Value getValue() {
        return value;
    }
}
