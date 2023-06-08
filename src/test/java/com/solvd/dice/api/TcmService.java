package com.solvd.dice.api;

import java.io.IOException;
import java.util.*;

import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;
import com.solvd.dice.api.dataSuite.TestCase;
import com.solvd.dice.api.dataSuite.TestSuite;
import com.solvd.dice.api.service.TcmTestCaseService;
import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.solvd.dice.api.tcmTestCasePojo.TestSuitePojo;
import lombok.extern.slf4j.Slf4j;

import static com.solvd.dice.api.DataSuiteService.casesParentsMapTcm;

@Slf4j
public class TcmService {

    public static HashMap<Integer, String> sectionsNamesMapTR = new HashMap<>();
    public static HashMap<Integer, Section> sectionsMapTR = new HashMap<>();
    public static HashMap<Integer, Suite> suitesMapTR = new HashMap<>();

    public ArrayList<Suite> getNotPresentSuiteTitles(List<String> suiteNamesTcm, List<Suite> suitesTR) {
        ArrayList<Suite> notPresentSuiteTitles = new ArrayList<>();
        for (Suite tss : suitesTR) {
            suitesMapTR.put(tss.getId(), tss);
            boolean contains = suiteNamesTcm.contains(tss.getName());
            if (contains) log.debug("TCM Contains Suite : " + tss.getName());
            else notPresentSuiteTitles.add(tss);
        }
        log.info("suitesTR size : " + suitesTR.size());
        log.info("suiteNamesTcm size : " + suiteNamesTcm.size());
        log.info("notPresentSuiteTitles size : " + notPresentSuiteTitles.size());
        return notPresentSuiteTitles;
    }

    public ArrayList<Section> getNotPresentSectionTitles(List<String> sectionNamesTcm, List<Section> sectionsTR) {
        ArrayList<Section> notPresentSectionTitles = new ArrayList<>();
        for (Section se : sectionsTR) {
            sectionsNamesMapTR.put(se.getId(), se.getName());
            sectionsMapTR.put(se.getId(), se);
            boolean contains = sectionNamesTcm.contains(se.getName());
            if (contains) {
               //sectionParentsMap.get(se.getId())
            }
            else notPresentSectionTitles.add(se);
        }
        log.info("sectionsTR size : " + sectionsTR.size());
        log.info("sectionNamesTcm size : " + sectionNamesTcm.size());
        log.info("notPresentSectionTitles size : " + notPresentSectionTitles.size());
        return notPresentSectionTitles;
    }

    public ArrayList<Case> getNotPresentCases(List<TestCase> testCasesTcm, List<String> allTitlesTcm, List<Case> allCasesTR) {
        ArrayList<Case> notPresentCases = new ArrayList<>(); //check hierarchy
        for (Case css : allCasesTR) {

            boolean contains = allTitlesTcm.contains(css.getTitle());
            if (contains) {
                for (TestCase title : testCasesTcm) {
                    boolean contains2 = sectionsNamesMapTR.get(css.getSectionId()).contains(casesParentsMapTcm.get(title.getId()));
                    boolean contains3 = css.getTitle().contains(title.getTitle());
                    if (contains3 && !contains2) notPresentCases.add(css);
                }
            } else notPresentCases.add(css);
        }
        log.info("allTitlesTR size : " + allCasesTR.size());
        log.info("allTitlesTcm size : " + allTitlesTcm.size());
        log.info("notPresentCases : " + notPresentCases.size());
        return notPresentCases;
    }

    public void createNotPresentSuites(List<Suite> notPresentSuiteTitles) {
        for (Suite tss : notPresentSuiteTitles) {
            TestSuitePojo tcmTestSuite = new TestSuitePojo();
            tcmTestSuite.setTitle(tss.getName());
            if (tss.getDescription() != null)
                tcmTestSuite.setDescription(tss.getDescription());
            else tcmTestSuite.setDescription("");
            tcmTestSuite.setParentSuiteId(0);

            ApiTest api = new ApiTest();
            api.testCreateTestSuite(tcmTestSuite);
        }
    }

    public void createNotPresentSubSuites(List<Section> notPresentSectionTitles, int tcmSuiteId) throws IOException {
        DataSuiteService dataSuiteService = new DataSuiteService();
        TestSuite[] tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes
        dataSuiteService.getSubSuites(tcmData, tcmSuiteId);
        ApiTest api = new ApiTest();
        int depthCount = 0;
        for (Section se : notPresentSectionTitles) {
            if (se.getDepth() > depthCount) depthCount = se.getDepth(); //getting max section depth
        }
        log.debug("depthCount : " + depthCount);
        for (Section se : notPresentSectionTitles) {
            if (se.getDepth() == 0) {
                TestSuitePojo tcmTestSubSuite = new TestSuitePojo();
                tcmTestSubSuite.setTitle(se.getName());
                if (se.getDescription() != null)
                    tcmTestSubSuite.setDescription(se.getDescription());
                else tcmTestSubSuite.setDescription("");
                tcmTestSubSuite.setParentSuiteId(tcmSuiteId);
                api.testCreateTestSuite(tcmTestSubSuite);
            }
            if (se.getDepth() > 0) {
                for (int i = 1; i <= depthCount; i++) {
                    if (se.getDepth() == i) {
                        if (notPresentSectionTitles.size() != 0){
                            tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes
                            dataSuiteService.getSubSuites(tcmData, tcmSuiteId);
                        }
                        TestSuitePojo tcmTestSubSuite = new TestSuitePojo();
                        tcmTestSubSuite.setTitle(se.getName());
                        if (se.getDescription() != null)
                            tcmTestSubSuite.setDescription(se.getDescription());
                        else tcmTestSubSuite.setDescription("");
                        int tcmSectionId = dataSuiteService.getSectionIdByTitle(tcmData, tcmSuiteId, sectionsNamesMapTR.get(se.getParentId()));
                        tcmTestSubSuite.setParentSuiteId(tcmSectionId);
                        api.testCreateTestSuite(tcmTestSubSuite);
                    }
                }
            }
        }
    }

    public void createNotPresentCases(ArrayList<Case> notPresentCases, TestSuite[] tcmData, int tcmSuiteId) {
        TcmTestCaseService setup = new TcmTestCaseService();
        DataSuiteService dataSuiteService = new DataSuiteService();
        dataSuiteService.getSubSuites(tcmData, tcmSuiteId);
        ApiTest api = new ApiTest();
        for (Case css : notPresentCases) {
            int tcmSectionId = dataSuiteService.getSectionIdByCase(tcmData, tcmSuiteId, css);
            TestCasePojo tcmCase = setup.setTcmTestCaseTest(css, tcmSectionId);
            api.testCreateTestCase(tcmCase);
        }
    }

}
