package com.solvd.dice.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.CaseType;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;
import com.solvd.dice.api.dataSuite.Tabs.Field;
import com.solvd.dice.api.dataSuite.Tabs.SettingsData;
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
    }

    @Test
    public void main() throws IOException {
        ApiTestRail apiTestRail = new ApiTestRail();
        DataSuiteService dataSuiteService = new DataSuiteService();
        TcmService tcmService = new TcmService();

        /**   CONFIGURING SETTINGS **/

        SettingsData settingsData = dataSuiteService.getTabsData();
        int tabId = dataSuiteService.getPropertiesTabId(settingsData);
        List<CaseType> caseTypes = apiTestRail.getCaseTypes();
        tcmService.createFields(dataSuiteService.getFields(settingsData), caseTypes, tabId);

        /**   CREATING NOT PRESENT SUITES **/

        List<Suite> suitesTR = apiTestRail.getSuites();
        TestSuite[] tcmData = dataSuiteService.getTestData(); //getting data from TCM
        ArrayList<String> suiteNamesTcm = dataSuiteService.getSuiteTitles(tcmData);
        ArrayList<Suite> notPresentSuiteTitles = tcmService.getNotPresentSuites(suiteNamesTcm, suitesTR);
        tcmService.createNotPresentSuites(notPresentSuiteTitles);

        if (notPresentSuiteTitles.size() !=0 ) tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes

        /**   IMPORTING SUITES **/
/*
        importSuite(7, suitesTR, tcmData);
        importSuite(8, suitesTR, tcmData);
        importSuite(9, suitesTR, tcmData);
 */
        importSuite(5, suitesTR, tcmData);
    }
}
