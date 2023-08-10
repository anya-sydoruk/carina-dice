package com.solvd.dice.api.dataSuite.launch;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Results {

    public final Result[] result;

    @JsonCreator
    public Results(@JsonProperty("results") Result[] result) {
        this.result = result;
    }

    public Result[] getResult() {
        return result;
    }

}
