package com.solvd.dice.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.codepine.api.testrail.model.Case;
import com.solvd.dice.api.dataSuite.DataSuite;
import com.solvd.dice.api.dataSuite.TestCase;
import com.solvd.dice.api.dataSuite.TestSuite;
import lombok.extern.slf4j.Slf4j;

import static com.solvd.dice.api.TcmService.*;

@Slf4j
public class DataSuiteService {

    public static int tcmId = 0;
    public static HashMap<Long, String> casesParentsMapTcm = new HashMap<>();
    public static HashMap<Long, String> sectionParentsMapTcm = new HashMap<>();

    public TestSuite[] getTestData() throws IOException {
        ApiTest apiTest = new ApiTest();
        apiTest.createToken();
        apiTest.testGetAllTestCases();

        DataSuite dataSuite = apiTest.dataSuite;
        return dataSuite.getTest().getTestSuites();
    }

    public List<TestCase> getSuiteTestCases(TestSuite[] tcmData, int tcmSuiteId) {
        List<TestCase> testCaseTitles = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            if (sui.getId().intValue() == tcmSuiteId) {
                TestCase[] cases = sui.getTestCases();
                for (TestCase cs : cases) {
                    casesParentsMapTcm.put(cs.getId(), sui.getTitle());
                }
                testCaseTitles.addAll(Arrays.asList(cases));
                testCaseTitles.addAll(getCases(sui));
            }
        }
        return testCaseTitles;
    }

    private List<TestCase> getCases(TestSuite sui) {
        List<TestCase> testCaseTitles = new ArrayList<>();
        TestSuite[] subSuites = sui.getChildTestSuites();
            for (TestSuite sub : subSuites) {
                TestCase[] cases = sub.getTestCases();
                for (TestCase cs : cases) {
                    casesParentsMapTcm.put(cs.getId(), sub.getTitle());
                }
                testCaseTitles.addAll(Arrays.asList(cases));
                testCaseTitles.addAll(getCases(sub));
        }
        return testCaseTitles;
    }

    public List<String> getSuiteTestCaseTitles(TestSuite[] tcmData, int tcmSuiteId) {
        List<TestCase> cases = getSuiteTestCases(tcmData, tcmSuiteId);
        ArrayList<String> caseTitles = new ArrayList<>();
        for (TestCase css : cases) {
            caseTitles.add(css.getTitle());
        }
        return caseTitles;
    }

    public ArrayList<String> getSuiteNames(TestSuite[] tcmData) {
        ArrayList<String> suiteNames = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            suiteNames.add(sui.getTitle());
        }
        return suiteNames;
    }

    public ArrayList<TestSuite> getSubSuites(TestSuite[] tcmData, int tcmSuiteId) {
        ArrayList<TestSuite> suiteNames = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            if (sui.getId().intValue() == tcmSuiteId) {
                TestSuite[] subSuites2 = sui.getChildTestSuites();
                for (TestSuite sub : subSuites2) {
                    suiteNames.add(sub);
                    sectionParentsMapTcm.put(sub.getId(), sui.getTitle());
                    suiteNames.addAll(getSubSuites(sub));
                }
            }
        }
        return suiteNames;
    }

    private ArrayList<TestSuite> getSubSuites(TestSuite sub) {
        ArrayList<TestSuite> suiteNames = new ArrayList<>();
        TestSuite[] subSuites = sub.getChildTestSuites();
        for (TestSuite sub2 : subSuites) {
            suiteNames.add(sub2);
            sectionParentsMapTcm.put(sub2.getId(), sub.getTitle());
            suiteNames.addAll(getSubSuites(sub2));
        }
        return suiteNames;
    }

    public ArrayList<String> getSubSuiteNames(TestSuite[] tcmData, int tcmSuiteId) {
        ArrayList<TestSuite> suites = getSubSuites(tcmData, tcmSuiteId);
        ArrayList<String> suiteTitles = new ArrayList<>();
        for (TestSuite sub : suites) {
            suiteTitles.add(sub.getTitle());
        }
        return suiteTitles;
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
            if (sui.getId().intValue() == tcmSuiteId) {
                TestSuite[] subSuites = sui.getChildTestSuites();
                for (TestSuite sub : subSuites) {
                    if (sub.getTitle().contains(title) && sui.getTitle().contains(sectionParentsMapTcm.get(sub.getId()))){
                        tcmId = Math.toIntExact(sub.getId());}
                    else getSectionIdByTitle(sub, title);
                }
            }
        }
        return tcmId;
    }

    private int getSectionIdByTitle(TestSuite sub2, String title) {
        TestSuite[] subSuites3 = sub2.getChildTestSuites();
        for (TestSuite sub3 : subSuites3) {
            if (sub3.getTitle().contains(title) && sub2.getTitle().contains(sectionParentsMapTcm.get(sub3.getId()))) {
                tcmId = Math.toIntExact(sub3.getId());
            }
            else getSectionIdByTitle(sub3, title);
        }
        return tcmId;
    }

    public int getSectionIdByCase(TestSuite[] tcmData, int tcmSuiteId, Case css) {
        String title = sectionsNamesMapTR.get(css.getSectionId());
        for (TestSuite sui : tcmData) {
            if (sui.getId().intValue() == tcmSuiteId) {
                TestSuite[] subSuites = sui.getChildTestSuites();
                String parentName;
                if (sectionsMapTR.get(css.getSectionId()).getParentId() != null)
                    parentName = sectionsMapTR.get(sectionsMapTR.get(css.getSectionId()).getParentId()).getName();
                else parentName = suitesMapTR.get(sectionsMapTR.get(css.getSectionId()).getSuiteId()).getName();
                for (TestSuite sub : subSuites) {
                    if (sub.getTitle().contains(title) && parentName.contains(sectionParentsMapTcm.get(sub.getId()))) {
                        tcmId = Math.toIntExact(sub.getId());
                    } else getSectionIdByCase(sub, css);
                }
            }
        }
        return tcmId;
    }

    private int getSectionIdByCase(TestSuite sub2, Case css) {
        String title = sectionsNamesMapTR.get(css.getSectionId());
        TestSuite[] subSuites3 = sub2.getChildTestSuites();
        for (TestSuite sub3 : subSuites3) {
            String parentName;
            if (sectionsMapTR.get(css.getSectionId()).getParentId() != null)
                parentName = sectionsMapTR.get(sectionsMapTR.get(css.getSectionId()).getParentId()).getName();
            else parentName = suitesMapTR.get(sectionsMapTR.get(css.getSectionId()).getSuiteId()).getName();
            if (sub3.getTitle().contains(title) && parentName.contains(sectionParentsMapTcm.get(sub3.getId()))){
                tcmId = Math.toIntExact(sub3.getId());
            }
            else getSectionIdByCase(sub3, css);
        }
        return tcmId;
    }
}
