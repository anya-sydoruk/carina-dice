package com.solvd.dice.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codepine.api.testrail.model.Section;
import com.solvd.dice.api.dataSuite.launch.Result;
import com.solvd.dice.api.dataSuite.launch.TestCases;
import com.solvd.dice.api.dataSuite.testSuites.DataSuite;
import com.solvd.dice.api.dataSuite.testSuites.TestCase;
import com.solvd.dice.api.dataSuite.testSuites.TestSuite;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSuiteService {

    public TestSuite[] getTestData() throws IOException {
        ApiTcm apiTcm = new ApiTcm();
        apiTcm.getAllTestCases();

        DataSuite dataSuite = apiTcm.dataSuite;
        return dataSuite.getTest().getTestSuites();
    }

    public ArrayList<String> getSuiteTitles(TestSuite[] tcmData) {
        ArrayList<String> suiteNames = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            suiteNames.add(sui.getTitle());
        }
        return suiteNames;
    }

    public ArrayList<TestSuite> getSubSuitesUpdatePath(TestSuite[] tcmData, int tcmSuiteId) {
        ArrayList<TestSuite> suites = getSubSuites(tcmData, tcmSuiteId);
        getSuitesPath(suites);
        return suites;
    }

    private ArrayList<TestSuite> getSubSuites(TestSuite[] tcmData, int tcmSuiteId) {
        ArrayList<TestSuite> suites = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            if (sui.getId().intValue() == tcmSuiteId) {
                TestSuite[] subSuites2 = sui.getChildTestSuites();
                for (TestSuite sub : subSuites2) {
                    suites.add(sub);
                    sub.setParentSuite(sui);
                    suites.addAll(getSubSuites(sub));
                }
            }
        }
        return suites;
    }

    private ArrayList<TestSuite> getSubSuites(TestSuite sub) {
        ArrayList<TestSuite> suites = new ArrayList<>();
        TestSuite[] subSuites = sub.getChildTestSuites();
        for (TestSuite sub2 : subSuites) {
            suites.add(sub2);
            sub2.setParentSuite(sub);
            suites.addAll(getSubSuites(sub2));
        }
        return suites;
    }

    private void getSuitesPath(ArrayList<TestSuite> suites) {
        for (TestSuite sui : suites) {
            String path = "";
            TestSuite originalSuite = sui;
            while (sui.getParentSuite() != null) {
                path = path + " < " + sui.getParentSuite().getTitle();
                sui = sui.getParentSuite();
            }
            originalSuite.setPath(path);
        }
    }

    public List<TestCase> getTestCases(TestSuite[] tcmData, int tcmSuiteId) {
        List<TestCase> testCases = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            if (sui.getId().intValue() == tcmSuiteId) {
                TestCase[] cases = sui.getTestCases();
                for (TestCase cs : cases) {
                    cs.setParentSuite(sui);
                }
                testCases.addAll(Arrays.asList(cases));
                testCases.addAll(getTestCases(sui));
            }
        }
        return testCases;
    }

    private List<TestCase> getTestCases(TestSuite sui) {
        List<TestCase> testCases = new ArrayList<>();
        TestSuite[] subSuites = sui.getChildTestSuites();
        for (TestSuite sub : subSuites) {
            TestCase[] cases = sub.getTestCases();
            for (TestCase cs : cases) {
                cs.setParentSuite(sub);
            }
            testCases.addAll(Arrays.asList(cases));
            testCases.addAll(getTestCases(sub));
        }
        return testCases;
    }

    public void getCasesPath(List<TestCase> cases) {
        for (TestCase cs : cases) {
            String path = cs.getParentSuite().getTitle() + cs.getParentSuite().getPath();
            cs.setPath(path);
        }
    }

    public int getSuiteIdByTitle(TestSuite[] tcmData, String title) {
        Long css = 0L;
        for (TestSuite sui : tcmData) {
            if (sui.getTitle().equals(title))
                css = sui.getId();
        }
        return Math.toIntExact(css);
    }

    public int getSectionId(ArrayList<TestSuite> sectionsTcm, Section se) {
        for (TestSuite sui : sectionsTcm) {
            boolean containsName = se.getName().equals(sui.getTitle());
            boolean containsParent = TcmService.sectionsPathTR.get(se.getId()).equals(sui.getPath());
            if (containsName && containsParent)
                return Math.toIntExact(sui.getId());
        }
        return -1;
    }

    public List<TestCases> getLaunchResults(int testRunId) throws IOException {
        ApiTcm apiTcm = new ApiTcm();
        apiTcm.getLaunchResults(testRunId);
        List<TestCases> testCasesResults = new ArrayList<>();
        for (Result res : apiTcm.result) {
            List<TestCases> casesTemp = new ArrayList<>(List.of(res.getTestCases()));
            for (TestCases tc : casesTemp)
                tc.setResultStatus(res.getStatus());
            testCasesResults.addAll(casesTemp);
        }
        return testCasesResults;
    }
}
