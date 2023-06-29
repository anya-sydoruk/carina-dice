package com.solvd.dice.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;
import com.solvd.dice.api.dataSuite.TestSuites.TestCase;
import com.solvd.dice.api.dataSuite.TestSuites.TestSuite;
import com.solvd.dice.api.service.ApiTestRail;
import com.solvd.dice.api.service.DataSuiteService;
import com.solvd.dice.api.service.TcmService;
import lombok.extern.slf4j.Slf4j;

import org.testng.annotations.Test;

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
        ArrayList<TestSuite> sectionsTcm = dataSuiteService.getSubSuitesUpdatePath(tcmData, tcmSuiteId);

        ArrayList<Section> notPresentSections = tcmService.getNotPresentSections(sectionsTcm, sectionsTR);
        tcmService.createNotPresentSubSuites(notPresentSections, tcmSuiteId);
        if (notPresentSections.size() != 0)
            tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes

        /**   CREATING CASES **/

        List<Case> casesTR = apiTestRail.getTestCases(trSuiteId);
        List<TestCase> testCasesTcm = dataSuiteService.getTestCases(tcmData, tcmSuiteId);

        ArrayList<Case> notPresentCases = tcmService.getNotPresentCases(testCasesTcm, casesTR);
        sectionsTcm = dataSuiteService.getSubSuitesUpdatePath(tcmData, tcmSuiteId);
        tcmService.createNotPresentCases(notPresentCases, sectionsTcm);

        /**   WRITING DATA INTO FILE **/

        if (notPresentCases.size() != 0)
            tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes
        dataSuiteService.getSubSuitesUpdatePath(tcmData, tcmSuiteId);
        testCasesTcm = dataSuiteService.getTestCases(tcmData, tcmSuiteId);
        tcmService.setCasesRelation(testCasesTcm, casesTR);
    }

    @Test
    public void main() throws IOException {
        ApiTestRail apiTestRail = new ApiTestRail();
        DataSuiteService dataSuiteService = new DataSuiteService();
        TcmService tcmService = new TcmService();

        /**   CREATING NOT PRESENT SUITES **/

        tcmService.setCaseTypes(apiTestRail.getCaseTypes());

        List<Suite> suitesTR = apiTestRail.getSuites();
        TestSuite[] tcmData = dataSuiteService.getTestData(); //getting data from TCM
        ArrayList<String> suiteNamesTcm = dataSuiteService.getSuiteTitles(tcmData);

        ArrayList<Suite> notPresentSuiteTitles = tcmService.getNotPresentSuites(suiteNamesTcm, suitesTR);
        tcmService.createNotPresentSuites(notPresentSuiteTitles);

        if (notPresentSuiteTitles.size() != 0)
            tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes

        /**   IMPORTING SUITES **/

        for (Suite suite : suitesTR)
            importSuite(suite.getId(), suitesTR, tcmData);
    }
}
