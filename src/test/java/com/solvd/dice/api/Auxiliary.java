package com.solvd.dice.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;
import com.solvd.dice.api.dataSuite.TestCase;
import com.solvd.dice.api.dataSuite.TestSuite;
import com.solvd.dice.api.service.ApiTestRail;
import com.solvd.dice.api.service.DataSuiteService;
import com.solvd.dice.api.service.TcmService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Auxiliary {

    public void importSuite(int trSuiteId, List<Suite> suitesTR, TestSuite[] tcmData) throws IOException {

        ApiTestRail apiTestRail = new ApiTestRail();
        DataSuiteService dataSuiteService = new DataSuiteService();
        TcmService tcmService = new TcmService();

        int tcmSuiteId = dataSuiteService.getSuiteIdByTitle(tcmData, suitesTR.get(trSuiteId - 1).getName());
        log.warn("tcmSuiteId Name : " + suitesTR.get(trSuiteId - 1).getName());

        /**   CREATING SECTIONS **/

        List<Section> sectionsTR = apiTestRail.getSections(trSuiteId);
        ArrayList<TestSuite> sectionsTcm = dataSuiteService.getSubSuites(tcmData, tcmSuiteId);
        ArrayList<Section> notPresentSections = tcmService.getNotPresentSections(sectionsTcm, sectionsTR);

        tcmService.createNotPresentSubSuites(notPresentSections, tcmSuiteId);
        if (notPresentSections.size() != 0)
            tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes

        /**   CREATING CASES **/

        List<Case> casesTR = apiTestRail.getTestCases(trSuiteId);
        List<TestCase> testCasesTcm = dataSuiteService.getTestCases(tcmData, tcmSuiteId);

        ArrayList<Case> notPresentCases = tcmService.getNotPresentCases(testCasesTcm, casesTR);
        tcmService.createNotPresentCases(notPresentCases, tcmData, tcmSuiteId);

        /**   FINAL CHECK **/
/*
        tcmData = dataSuiteService.getTestData(); //final updating data from TCM
        allTitlesTcm = dataSuiteService.getSuiteTestCaseTitles(tcmData, tcmSuiteId);
        tcmService.getNotPresentCases(allTestCases, allTitlesTcm, allTitlesTR);
*/
    }

    public static void main(String[] args) throws IOException {
        ApiTestRail apiTestRail = new ApiTestRail();
        DataSuiteService dataSuiteService = new DataSuiteService();
        TcmService tcmService = new TcmService();
        Auxiliary aux = new Auxiliary();

        /**   GETTING AND CHECKING SUITES **/

        List<Suite> suitesTR = apiTestRail.getSuites();

        TestSuite[] tcmData = dataSuiteService.getTestData(); //getting data from TCM
        ArrayList<String> suiteNamesTcm = dataSuiteService.getSuiteTitles(tcmData);
        ArrayList<Suite> notPresentSuiteTitles = tcmService.getNotPresentSuites(suiteNamesTcm, suitesTR);

        /**   CREATING NOT PRESENT SUITES **/

        tcmService.createNotPresentSuites(notPresentSuiteTitles);

        if (notPresentSuiteTitles.size() !=0 ) tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes

        /**   IMPORTING SUITES **/

        aux.importSuite(7, suitesTR, tcmData);
        aux.importSuite(8, suitesTR, tcmData);
        aux.importSuite(9, suitesTR, tcmData);
    }
}
