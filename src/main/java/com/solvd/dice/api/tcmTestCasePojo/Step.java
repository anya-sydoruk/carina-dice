package com.solvd.dice.api.tcmTestCasePojo;

public class Step {

    private String type;
    private int relativePosition;
    private RegularStep regularStep;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(int relativePosition) {
        this.relativePosition = relativePosition;
    }

    public RegularStep getRegularStep() {
        return regularStep;
    }

    public void setRegularStep(RegularStep regularStep) {
        this.regularStep = regularStep;
    }
}
