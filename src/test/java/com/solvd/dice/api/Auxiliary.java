package com.solvd.dice.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Auxiliary {

    public List<String> getAllTestCasesTitles() throws IOException {
        ApiTest apiTest = new ApiTest();
        apiTest.createToken();
        apiTest.testGetAllTestCases();

        DataSuite dataSuite = apiTest.dataSuite;
        TestSuite[] names = dataSuite.getTest().getTestSuites();

        List<String> testCaseTitles = new ArrayList<>();
        for (TestSuite sui : names) {
            TestCase[] css = sui.getTestCases();
            for (TestCase testCase : css) {
                String title = testCase.getTitle();
                testCaseTitles.add(title);
            }
        }
        return testCaseTitles;
    }

    public static void main(String[] args) throws IOException {
        ApiTestRailTest apiTestRailTest = new ApiTestRailTest();
        apiTestRailTest.getTestCaseTitleTest();
        String testString = apiTestRailTest.testCaseTitle;

        Auxiliary aux = new Auxiliary();
        List<String> allTitles = aux.getAllTestCasesTitles();

        boolean contains = allTitles.contains(testString);
    }
}
