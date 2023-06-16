package com.solvd.dice.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.codepine.api.testrail.model.Section;
import com.solvd.dice.api.dataSuite.Tabs.Field;
import com.solvd.dice.api.dataSuite.Tabs.Tab;
import com.solvd.dice.api.dataSuite.Tabs.SettingsData;
import com.solvd.dice.api.dataSuite.TestSuites.DataSuite;
import com.solvd.dice.api.dataSuite.TestSuites.TestCase;
import com.solvd.dice.api.dataSuite.TestSuites.TestSuite;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSuiteService {

    public static int tcmId = 0;
    public static HashMap<Long, String> casesParentsMapTcm = new HashMap<>();
    public static HashMap<Long, String> sectionParentsMapTcm = new HashMap<>();

    public static Field[] fields;


    public TestSuite[] getTestData() throws IOException {
        ApiTcm apiTcm = new ApiTcm();
        apiTcm.testGetAllTestCases();

        DataSuite dataSuite = apiTcm.dataSuite;
        return dataSuite.getTest().getTestSuites();
    }

    public SettingsData getTabsData() throws IOException {
        ApiTcm apiTcm = new ApiTcm();
        apiTcm.createToken();
        apiTcm.testGetAllCustomFields();

        return apiTcm.settingsData;
    }

    public int getPropertiesTabId(SettingsData settingsData){
        Tab[] tabs = settingsData.getSettings().getTabs();
        for (Tab tab : tabs){
            if (tab.getName().contains("Properties"))
                return tab.getId();
        }
        return 0;
    }

    public Field[] getFields(SettingsData settingsData){
        fields = settingsData.getSettings().getFields();
        return fields;
    }

    public ArrayList<String> getSuiteTitles(TestSuite[] tcmData) {
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

    public ArrayList<String> getSubSuiteTitles(ArrayList<TestSuite> suites) {
        ArrayList<String> suiteTitles = new ArrayList<>();
        for (TestSuite sub : suites) {
            suiteTitles.add(sub.getTitle());
        }
        return suiteTitles;
    }

    public List<TestCase> getTestCases(TestSuite[] tcmData, int tcmSuiteId) {
        List<TestCase> testCases = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            if (sui.getId().intValue() == tcmSuiteId) {
                TestCase[] cases = sui.getTestCases();
                for (TestCase cs : cases) {
                    casesParentsMapTcm.put(cs.getId(), sui.getTitle());
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
                    casesParentsMapTcm.put(cs.getId(), sub.getTitle());
                }
                testCases.addAll(Arrays.asList(cases));
                testCases.addAll(getTestCases(sub));
        }
        return testCases;
    }

    public List<String> getTestCaseTitles(List<TestCase> cases) {
        ArrayList<String> caseTitles = new ArrayList<>();
        for (TestCase css : cases) {
            caseTitles.add(css.getTitle());
        }
        return caseTitles;
    }

    public int getSuiteIdByTitle(TestSuite[] tcmData, String title) {
        Long css = 0L;
        for (TestSuite sui : tcmData) {
            if (sui.getTitle().contains(title))
                css = sui.getId();
        }
        int suiteId = Math.toIntExact(css);
        return suiteId;
    }

    public int getSectionId(TestSuite[] tcmData, int tcmSuiteId, Section se) {
        String title = TcmService.sectionsMapTR.get(se.getId()).getName();
        for (TestSuite sui : tcmData) {
            if (sui.getId().intValue() == tcmSuiteId) {
                TestSuite[] subSuites = sui.getChildTestSuites();
                String parentName;
                if (TcmService.sectionsMapTR.get(se.getId()).getParentId() != null)
                    parentName = TcmService.sectionsMapTR.get(TcmService.sectionsMapTR.get(se.getId()).getParentId()).getName();
                else parentName = TcmService.suitesMapTR.get(TcmService.sectionsMapTR.get(se.getId()).getSuiteId()).getName();
                for (TestSuite sub : subSuites) {
                    if (sub.getTitle().contains(title) && parentName.contains(sectionParentsMapTcm.get(sub.getId()))) {
                        tcmId = Math.toIntExact(sub.getId());
                    } else getSectionId(sub, se);
                }
            }
        }
        return tcmId;
    }

    private int getSectionId(TestSuite sub2, Section se) {
        String title = TcmService.sectionsMapTR.get(se.getId()).getName();
        TestSuite[] subSuites3 = sub2.getChildTestSuites();
        for (TestSuite sub3 : subSuites3) {
            String parentName;
            if (TcmService.sectionsMapTR.get(se.getId()).getParentId() != null)
                parentName = TcmService.sectionsMapTR.get(TcmService.sectionsMapTR.get(se.getId()).getParentId()).getName();
            else parentName = TcmService.suitesMapTR.get(TcmService.sectionsMapTR.get(se.getId()).getSuiteId()).getName();
            if (sub3.getTitle().contains(title) && parentName.contains(sectionParentsMapTcm.get(sub3.getId()))){
                tcmId = Math.toIntExact(sub3.getId());
            }
            else getSectionId(sub3, se);
        }
        return tcmId;
    }
}
