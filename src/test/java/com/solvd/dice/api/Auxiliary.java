package com.solvd.dice.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;
import com.solvd.dice.api.dataSuite.TestSuite;
import com.solvd.dice.api.service.TcmTestCaseService;
import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.solvd.dice.api.tcmTestCasePojo.TestSuitePojo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Auxiliary {

    public static int tcmSuiteId = 0;
    public static int tcmSectionId = 0;

    public static void main(String[] args) throws IOException {
        ApiTestRailTest apiTestRailTest = new ApiTestRailTest();
        DataSuiteService dataSuiteService = new DataSuiteService();
        ApiTest api = new ApiTest();

        /**   GETTING SUITES **/

        List<Suite> suitesTR = apiTestRailTest.getSuitesTest();
        int id = suitesTR.get(suitesTR.size() - 1).getId();
        log.info("Test Rail Suites count: " + id);

        /**   CHECKING SUITES **/

        TestSuite[] tcmData = dataSuiteService.getTestSuites(); //from TCM
        ArrayList<String> suiteNamesTcm = dataSuiteService.getSuiteNames(tcmData);
        ArrayList<Suite> notPresentSuiteTitles = new ArrayList<>();

        for (Suite tss : suitesTR){
            boolean contains = suiteNamesTcm.contains(tss.getName());
            if (contains) log.info("TCM Contains suite : " + tss.getName());
            else notPresentSuiteTitles.add(tss);
        }
        log.info("notPresentSuiteTitles size : " + notPresentSuiteTitles.size());

        /**   CREATING / POSTING NOT PRESENT SUITES **/

        for (Suite tss : notPresentSuiteTitles){
            TestSuitePojo tcmTestSuite = new TestSuitePojo();
            tcmTestSuite.setTitle(tss.getName());
            if (tss.getDescription() != null)
            tcmTestSuite.setDescription(tss.getDescription());
            else tcmTestSuite.setDescription("");
            tcmTestSuite.setParentSuiteId(0);
            //api.testCreateTestSuite(tcmTestSuite);
        }

        tcmData = dataSuiteService.getTestSuites(); //updating data from TCM

        /**   CHECKING SECTIONS **/

        int currTrSuiteId = 1; //start loop for all suites. post subsuites, cases, repeat.
        tcmSuiteId = dataSuiteService.getSuiteIdByTitle(tcmData, suitesTR.get(currTrSuiteId).getName());

        List<Section> sectionsTR = apiTestRailTest.getSectionsTest(currTrSuiteId);

        ArrayList<String> sectionNamesTcm = dataSuiteService.getSubSuiteNames(tcmData, tcmSuiteId);
        ArrayList<Section> notPresentSectionTitles = new ArrayList<>();
        Map<Integer, String> sectionsMap = new HashMap<>();

        for (Section se : sectionsTR){
            sectionsMap.put(se.getId(), se.getName());
            boolean contains = sectionNamesTcm.contains(se.getName());
            if (contains) log.info("TCM Contains SubSuite : " + se.getName());
            else notPresentSectionTitles.add(se);
        }
        log.info("notPresentSectionTitles size : " + notPresentSectionTitles.size());

        /**   CREATING NOT PRESENT SECTIONS **/
        /**   NO DEPTH SECTIONS **/

        for (Section se : notPresentSectionTitles) {
            if (se.getDepth() == 0) {
                TestSuitePojo tcmTestSubSuite = new TestSuitePojo();
                tcmTestSubSuite.setTitle(se.getName());
                if (se.getDescription() != null)
                    tcmTestSubSuite.setDescription(se.getDescription());
                else tcmTestSubSuite.setDescription("");
                tcmTestSubSuite.setParentSuiteId(tcmSuiteId);
                //api.testCreateTestSuite(tcmTestSubSuite);
            }
        }

        //tcmData = dataSuiteService.getTestSuites(); //updating data from TCM

        /**   DEPTH SECTIONS **/

        for (Section se : notPresentSectionTitles) {
            if (se.getDepth() == 1) {
                TestSuitePojo tcmTestSubSuite = new TestSuitePojo();
                tcmTestSubSuite.setTitle(se.getName());
                if (se.getDescription() != null)
                    tcmTestSubSuite.setDescription(se.getDescription());
                else tcmTestSubSuite.setDescription("");
                tcmSectionId = dataSuiteService.getSectionIdByTitle(tcmData, tcmSuiteId, sectionsMap.get(se.getParentId()));
                tcmTestSubSuite.setParentSuiteId(tcmSectionId);
                //api.testCreateTestSuite(tcmTestSubSuite);
            }
        }

        //tcmData = dataSuiteService.getTestSuites(); //updating data from TCM

        /**   CHECKING CASES **/

        List<Case> allTitlesTR = apiTestRailTest.getTestCasesTest(currTrSuiteId);

        List<String> allTitlesTcm = dataSuiteService.getAllTestCasesTitles(tcmData);
        ArrayList<Case> notPresentCases= new ArrayList<>();
        Map<String, Integer> casesSectionsMap = new HashMap<>();

        for (Case css : allTitlesTR) {
            casesSectionsMap.put(css.getTitle(), css.getSectionId());
            boolean contains = allTitlesTcm.contains(css.getTitle());
            if (contains) log.info("Contains title : " + css.getTitle());
            else notPresentCases.add(css);
        }

        log.info("allTitlesTR : " + allTitlesTR.size());
        log.info("notPresentCases : " + notPresentCases.size());

        /**   CREATING / POSTING NOT PRESENT CASES **/

        for (Case css : notPresentCases) {
            TcmTestCaseService setup = new TcmTestCaseService();
            tcmSectionId = dataSuiteService.getSectionIdByTitle(tcmData, tcmSuiteId, sectionsMap.get(casesSectionsMap.get(css.getTitle())));
            TestCasePojo tcmCase = setup.setTcmTestCaseTest(css, tcmSectionId);
            api.testCreateTestCase(tcmCase);
        }
    }
}
