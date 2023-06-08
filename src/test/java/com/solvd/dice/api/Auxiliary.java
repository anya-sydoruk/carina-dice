package com.solvd.dice.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;
import com.solvd.dice.api.dataSuite.TestCase;
import com.solvd.dice.api.dataSuite.TestSuite;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Auxiliary {

    public void importSuite(int trSuiteId, List<Suite> suitesTR, TestSuite[] tcmData) throws IOException {
        ApiTestRailTest apiTestRailTest = new ApiTestRailTest();
        DataSuiteService dataSuiteService = new DataSuiteService();
        TcmService tcmService = new TcmService();

        int tcmSuiteId = dataSuiteService.getSuiteIdByTitle(tcmData, suitesTR.get(trSuiteId - 1).getName());
        log.info("tcmSuiteId Name : " + suitesTR.get(trSuiteId - 1).getName());
        log.info("tcmSuiteId : " + tcmSuiteId);

        /**   CREATING SECTIONS **/

        List<Section> sectionsTR = apiTestRailTest.getSections(trSuiteId);
        ArrayList<String> sectionNamesTcm = dataSuiteService.getSubSuiteNames(tcmData, tcmSuiteId);
        ArrayList<Section> notPresentSectionTitles = tcmService.getNotPresentSectionTitles(sectionNamesTcm, sectionsTR);

        tcmService.createNotPresentSubSuites(notPresentSectionTitles, tcmSuiteId);

        if (notPresentSectionTitles.size() != 0)
            tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes

        /**   CREATING CASES **/

        List<Case> allTitlesTR = apiTestRailTest.getTestCases(trSuiteId);
        List<TestCase> allTestCases = dataSuiteService.getSuiteTestCases(tcmData, tcmSuiteId);

        List<String> allTitlesTcm = dataSuiteService.getSuiteTestCaseTitles(tcmData, tcmSuiteId);
        ArrayList<Case> notPresentCases = tcmService.getNotPresentCases(allTestCases, allTitlesTcm, allTitlesTR);
        //ArrayList<Case> notPresentCases = tcmService.getNotPresentCases(allTitlesTcm, allTitlesTR);

        tcmService.createNotPresentCases(notPresentCases, tcmData, tcmSuiteId);

        /**   FINAL CHECK **/

        tcmData = dataSuiteService.getTestData(); //final updating data from TCM
        allTitlesTcm = dataSuiteService.getSuiteTestCaseTitles(tcmData, tcmSuiteId);
        tcmService.getNotPresentCases(allTestCases, allTitlesTcm, allTitlesTR);

    }

    public static void main(String[] args) throws IOException {
        ApiTestRailTest apiTestRailTest = new ApiTestRailTest();
        DataSuiteService dataSuiteService = new DataSuiteService();
        TcmService tcmService = new TcmService();

        /**   GETTING AND CHECKING SUITES **/

        List<Suite> suitesTR = apiTestRailTest.getSuites();
        int id = suitesTR.get(suitesTR.size() - 1).getId();
        log.info("Test Rail Suites count: " + id);

        TestSuite[] tcmData = dataSuiteService.getTestData(); //from TCM
        ArrayList<String> suiteNamesTcm = dataSuiteService.getSuiteNames(tcmData);
        ArrayList<Suite> notPresentSuiteTitles = tcmService.getNotPresentSuiteTitles(suiteNamesTcm, suitesTR);

        /**   CREATING / POSTING NOT PRESENT SUITES **/

        tcmService.createNotPresentSuites(notPresentSuiteTitles);

        if (notPresentSuiteTitles.size() !=0 ) tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes

        //start loop for all suites. post subsuites, cases, repeat.

        /**   CHECKING SECTIONS **/

        Auxiliary aux = new Auxiliary();
        aux.importSuite(1, suitesTR, tcmData);

    }
}
