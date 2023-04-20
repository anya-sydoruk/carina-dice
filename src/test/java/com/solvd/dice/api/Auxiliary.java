package com.solvd.dice.api;

import java.io.IOException;

public class Auxiliary {

    public static void main(String[] args) throws IOException {
        ApiTestRailTest apiTestRailTest = new ApiTestRailTest();
        apiTestRailTest.getTestCaseTitleTest();
        String testString = apiTestRailTest.testCaseTitle;

        ApiTest apiTest = new ApiTest();
        apiTest.testGetAllTestCases();





    }
}
