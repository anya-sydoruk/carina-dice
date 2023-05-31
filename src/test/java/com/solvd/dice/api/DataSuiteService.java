package com.solvd.dice.api;

import com.solvd.dice.api.dataSuite.DataSuite;
import com.solvd.dice.api.dataSuite.TestCase;
import com.solvd.dice.api.dataSuite.TestSuite;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DataSuiteService {

    public TestSuite[] getTestSuites()throws IOException {
        ApiTest apiTest = new ApiTest();
        apiTest.createToken();
        apiTest.testGetAllTestCases();

        DataSuite dataSuite = apiTest.dataSuite;
        return dataSuite.getTest().getTestSuites();
    }

    public List<String> getAllTestCasesTitles(TestSuite[] tcmData) {
        List<String> testCaseTitles = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            TestCase[] css = sui.getTestCases();
            for (TestCase testCase : css) {
                testCaseTitles.add(testCase.getTitle());
            }
            TestSuite[] subSuites = sui.getChildTestSuites();
            for (TestSuite sub : subSuites) {
                TestCase[] css2 = sub.getTestCases();
                for (TestCase testCase : css2) {
                    testCaseTitles.add(testCase.getTitle());
                }
                TestSuite[] subSuites2 = sub.getChildTestSuites();
                for (TestSuite sub2 : subSuites2) {
                    TestCase[] css3 = sub2.getTestCases();
                    for (TestCase testCase : css3) {
                        testCaseTitles.add(testCase.getTitle());
                    }
                }
            }
        }
        return testCaseTitles;
    }

    public int getSuiteIdByTitle(TestSuite[] tcmData, String title) {
        Long css = 0L;
        for (TestSuite sui : tcmData) {
            if (sui.getTitle().contains(title))
                css = sui.getId();
        }
        int suiteId = Math.toIntExact(css);
        log.debug("SuiteId '" + title + "' : " + css);
        return suiteId;
    }

    public int getSectionIdByTitle(TestSuite[] tcmData, int tcmSuiteId, String title) {
        for (TestSuite sui : tcmData) {
            if (sui.getId() == tcmSuiteId) {
                TestSuite[] subSuites = sui.getChildTestSuites();
                for (TestSuite sub : subSuites) {
                    if (sub.getTitle().contains(title))
                        return Math.toIntExact(sub.getId());
                    TestSuite[] subSuites2 = sub.getChildTestSuites();
                    for (TestSuite sub2 : subSuites2) {
                        if (sub2.getTitle().contains(title))
                            return Math.toIntExact(sub2.getId());
                    }
                }
            }
        }
        return 0;
    }

    public ArrayList<String> getSuiteNames(TestSuite[] tcmData){
        ArrayList<String> suiteNames = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            suiteNames.add(sui.getTitle());
        }
        return suiteNames;
    }

    public ArrayList<String> getSubSuiteNames(TestSuite[] tcmData, int tcmSuiteId) {
        ArrayList<String> suiteNames = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            if (sui.getId().intValue() == tcmSuiteId) {
                TestSuite[] subSuites2 = sui.getChildTestSuites();
                for (TestSuite sub : subSuites2) {
                    suiteNames.add(sub.getTitle());
                    TestSuite[] subSuites3 = sub.getChildTestSuites();
                    for (TestSuite sub2 : subSuites3) {
                        suiteNames.add(sub2.getTitle());
                    }
                }
            }
        }
        return suiteNames;
    }
}
