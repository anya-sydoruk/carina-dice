package com.solvd.dice.api.tcmCustomField;

public class CustomField {
    private int tabId;
    private String name;
    private String dataType;
    private Value value;

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public int getTabId() {
        return tabId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
