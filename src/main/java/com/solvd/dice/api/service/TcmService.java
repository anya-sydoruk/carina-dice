package com.solvd.dice.api.service;

import java.io.IOException;
import java.util.*;

import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;
import com.codepine.api.testrail.model.CaseType;
import com.solvd.dice.api.dataSuite.TestSuites.TestCase;
import com.solvd.dice.api.dataSuite.TestSuites.TestSuite;
import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.solvd.dice.api.tcmTestCasePojo.TestSuitePojo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcmService {

    public static HashMap<Integer, String> caseTypesMapTR = new HashMap<>();
    public static HashMap<Integer, Suite> suitesMapTR = new HashMap<>();
    public static HashMap<Integer, Section> sectionsMapTR = new HashMap<>();
    public static HashMap<Integer, String> sectionsPathTR = new HashMap<>();
    public static HashMap<Integer, String> casesPathTR = new HashMap<>();

    public void setCaseTypes(List<CaseType> caseTypes){
        for (CaseType ct : caseTypes) {
            caseTypesMapTR.put(ct.getId(), ct.getName());
        }
    }

    public void setSectionsPathTr(List<Section> sectionsTR) {
        for (Section se : sectionsTR) {
            sectionsMapTR.put(se.getId(), se);
        }
        for (Section se : sectionsTR) {
            String path = "";
            Section originalSection = se;
            while (se.getParentId() != null) {
                se = sectionsMapTR.get(se.getParentId());
                path = path + " < " + se.getName();
            }
            path = path + " < " + suitesMapTR.get(se.getSuiteId()).getName();
            sectionsPathTR.put(originalSection.getId(), path);
        }
    }

    public void setCasesPathTr(List<Case> testCasesTR) {
        for (Case cs : testCasesTR) {
            String path = sectionsMapTR.get(cs.getSectionId()).getName();
            path = path + sectionsPathTR.get(cs.getSectionId());
            casesPathTR.put(cs.getId(), path);
        }
    }

    public ArrayList<Suite> getNotPresentSuites(List<String> suiteNamesTcm, List<Suite> suitesTR) {
        ArrayList<Suite> notPresentSuites = new ArrayList<>();
        for (Suite tss : suitesTR) {
            suitesMapTR.put(tss.getId(), tss);
            boolean containsName = false;
            for (String name : suiteNamesTcm) {
                if (name.equals(tss.getName())) containsName = true;
            }
            if (!containsName) notPresentSuites.add(tss);
        }
        log.warn("Suites TR : " + suitesTR.size() + ". Suites TCM : " + suiteNamesTcm.size() + ". Not present: " + notPresentSuites.size());
        return notPresentSuites;
    }

    public ArrayList<Section> getNotPresentSections(ArrayList<TestSuite> sectionsTcm, List<Section> sectionsTR) {
        DataSuiteService dataSuiteService = new DataSuiteService();
        ArrayList<Section> notPresentSectionTitles = new ArrayList<>();

        setSectionsPathTr(sectionsTR);
        dataSuiteService.getSuitesPath(sectionsTcm);

        for (Section se : sectionsTR) {
            boolean contains = false;
            for (TestSuite sui : sectionsTcm) {
                boolean containsName = se.getName().equals(sui.getTitle());
                boolean containsParent = sectionsPathTR.get(se.getId()).equals(sui.getPath());
                if (containsName && containsParent) contains = true;
            }
            if (!contains) notPresentSectionTitles.add(se);

        }
        log.warn("Sections TR : " + sectionsTR.size() + ". Sections TCM : " + sectionsTcm.size() + ". Not present : " + notPresentSectionTitles.size());
        return notPresentSectionTitles;
    }

    public ArrayList<Case> getNotPresentCases(List<TestCase> testCasesTcm, List<Case> testCasesTR) {
        DataSuiteService dataSuiteService = new DataSuiteService();
        ArrayList<Case> notPresentCases = new ArrayList<>();
        setCasesPathTr(testCasesTR);
        dataSuiteService.getCasesPath(testCasesTcm);

        for (Case css : testCasesTR) {
            boolean contains = false;
            for (TestCase caseTcm : testCasesTcm) {
                boolean containsName = css.getTitle().equals(caseTcm.getTitle());
                boolean containsParent = casesPathTR.get(css.getId()).equals(caseTcm.getPath());
                if (containsName && containsParent) contains = true;
            }
            if (!contains) notPresentCases.add(css);
        }

        log.warn("Test Cases TR : " + testCasesTR.size() + ". Test Cases TCM : " + testCasesTcm.size() + ". Not present : " + notPresentCases.size());
        return notPresentCases;
    }

    public void createNotPresentSuites(List<Suite> notPresentSuites) {
        for (Suite tss : notPresentSuites) {
            TestSuitePojo tcmTestSuite = new TestSuitePojo();
            tcmTestSuite.setTitle(tss.getName());
            tcmTestSuite.setDescription(setNotNullLine(tss.getDescription()));
            tcmTestSuite.setParentSuiteId(0);

            ApiTcm api = new ApiTcm();
            api.testCreateTestSuite(tcmTestSuite);
        }
    }

    public void createNotPresentSubSuites(List<Section> notPresentSections, int tcmSuiteId, ArrayList<TestSuite> sectionsTcm) throws IOException {
        DataSuiteService dataSuiteService = new DataSuiteService();
        TestSuite[] tcmData;
        if (notPresentSections.size() != 0) {
            tcmData = dataSuiteService.getTestData();} //updating data from TCM if there are any changes
        ApiTcm api = new ApiTcm();
        int depthCount = 0;
        for (Section se : notPresentSections) {
            if (se.getDepth() > depthCount) depthCount = se.getDepth(); //getting max section depth
        }
        for (Section se : notPresentSections) {
            if (se.getDepth() == 0) {
                TestSuitePojo tcmTestSubSuite = new TestSuitePojo();
                tcmTestSubSuite.setTitle(se.getName());
                tcmTestSubSuite.setDescription(setNotNullLine(se.getDescription()));
                tcmTestSubSuite.setParentSuiteId(tcmSuiteId);
                api.testCreateTestSuite(tcmTestSubSuite);
            }
        }
        for (Section se : notPresentSections) {
            if (se.getDepth() > 0) {
                for (int i = 1; i <= depthCount; i++) {
                    if (se.getDepth() == i) {
                        if (notPresentSections.size() != 0){
                            tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes
                            dataSuiteService.getSubSuites(tcmData, tcmSuiteId);
                        }
                        TestSuitePojo tcmTestSubSuite = new TestSuitePojo();
                        tcmTestSubSuite.setTitle(se.getName());
                        tcmTestSubSuite.setDescription(setNotNullLine(se.getDescription()));
                        int tcmParentId = dataSuiteService.getSectionId(sectionsTcm, sectionsMapTR.get(se.getParentId()));
                        tcmTestSubSuite.setParentSuiteId(tcmParentId);
                        api.testCreateTestSuite(tcmTestSubSuite);
                    }
                }
            }
        }
    }

    public void createNotPresentCases(ArrayList<Case> notPresentCases, ArrayList<TestSuite> sectionsTcm) {
        TcmTestCaseService setup = new TcmTestCaseService();
        DataSuiteService dataSuiteService = new DataSuiteService();
        ApiTcm api = new ApiTcm();
        for (Case css : notPresentCases) {
            int tcmSectionId = dataSuiteService.getSectionId(sectionsTcm, sectionsMapTR.get(css.getSectionId()));
            TestCasePojo tcmCase = setup.setTcmTestCaseTest(css, tcmSectionId);
            api.testCreateTestCase(tcmCase);
        }
    }

    private String setNotNullLine(String line) {
        if (line != null)
            return line;
        else return "";
    }

}
