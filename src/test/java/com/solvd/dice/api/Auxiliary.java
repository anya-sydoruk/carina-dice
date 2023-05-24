package com.solvd.dice.api;


import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;
import com.solvd.dice.api.service.TcmTestCaseSetup;
import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.solvd.dice.api.tcmTestCasePojo.TestSuitePojo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Auxiliary {

    public static int tcmSuiteId = 0;
    public static int tcmSectionId = 0;

    public TestSuite[] getTestSuites()throws IOException {
        ApiTest apiTest = new ApiTest();
        apiTest.createToken();
        apiTest.testGetAllTestCases();

        DataSuite dataSuite = apiTest.dataSuite;
        return dataSuite.getTest().getTestSuites();
    }

    public List<String> getAllTestCasesTitles(TestSuite[] tcmData){
        List<String> testCaseTitles = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            TestCase[] css = sui.getTestCases();
            for (TestCase testCase : css) {
                String title = testCase.getTitle();
                testCaseTitles.add(title);
            }
        }
        return testCaseTitles;
    }

    public int getSuiteIdByTitle(TestSuite[] tcmData, String title) {
        Long css = 0L;
        for (TestSuite sui : tcmData) {
            if (sui.getTitle().contains(title))
                css = sui.getId();
        }
        int suiteId = Math.toIntExact(css);
        log.info("suiteId " + title + " : " + css);

        tcmSuiteId = suiteId;
        return suiteId;
    }

    public int getSectionIdByTitle(TestSuite[] tcmData, String title) {
        Long css = 0L;
        for (TestSuite sui : tcmData) {
            if (sui.getTitle().contains(title))
                css = sui.getId();
        }
        int suiteId = Math.toIntExact(css);
        log.info("suiteId " + title + " : " + css);

        tcmSectionId = suiteId;
        return suiteId;
    }

    public ArrayList<String>  getSuiteNames(TestSuite[] tcmData){
        ArrayList<String> suiteNames = new ArrayList<>();
        for (TestSuite sui : tcmData) {
            suiteNames.add(sui.getTitle());
        }
        return suiteNames;
    }

    public ArrayList<String> getSubSuiteNames(TestSuite tcmData){
        ArrayList<String> suiteNames = new ArrayList<>();
        TestSuite[] subSuites = tcmData.getChildTestSuites();
        for (TestSuite sui : subSuites) {
            suiteNames.add(sui.getTitle());
        }
        return suiteNames;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ApiTestRailTest apiTestRailTest = new ApiTestRailTest();

        /**   GETTING SUITES **/

        List<Suite> suitesTR = apiTestRailTest.getSuitesTest();
        int id = suitesTR.get(suitesTR.size() - 1).getId();
        System.out.println("Suites count: " + id);

        /**   CHECKING SUITES **/

        Auxiliary aux = new Auxiliary();
        TestSuite[] tcmData = aux.getTestSuites(); //from TCM
        ArrayList<String> suiteNamesTcm = aux.getSuiteNames(tcmData);
        ArrayList<Suite> notPresentSuiteTitles = new ArrayList<>();

        for (Suite tss : suitesTR){
            boolean contains = suiteNamesTcm.contains(tss.getName());
            if (contains) log.info("Contains suite : " + tss.getName());
            else notPresentSuiteTitles.add(tss);
        }

        /**   CREATING NOT PRESENT SUITES **/

        for (Suite tss : notPresentSuiteTitles){
            TestSuitePojo tcmTestSuite = new TestSuitePojo();
            tcmTestSuite.setTitle(tss.getName());
            tcmTestSuite.setDescription(tss.getDescription());
        }

        /**   POSTING SUITES **/
/*
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/api/post/postTcmTestSuite.json"), tcmTestSuite);
        ApiTest api = new ApiTest();
        api.testCreateTestSuite();

 */

        /**   CHECKING SECTIONS **/

        int currTrSuiteId = 1;

        tcmSuiteId = aux.getSuiteIdByTitle(tcmData, suitesTR.get(currTrSuiteId).getName());

        List<Section> sectionsTR = apiTestRailTest.getSectionsTest(currTrSuiteId);
        log.info(sectionsTR.get(1).getName());

        ArrayList<String> sectionNamesTcm = aux.getSubSuiteNames(tcmData[currTrSuiteId - 1]);
        ArrayList<Section> notPresentSectionTitles = new ArrayList<>();

        for (Section se : sectionsTR){
            boolean contains = sectionNamesTcm.contains(se.getName());
            if (contains) log.info("Contains SubSuite : " + se.getName());
            else notPresentSectionTitles.add(se);
        }

        /**   CREATING NOT PRESENT SECTIONS **/

        for (Section se  : notPresentSectionTitles){
            TestSuitePojo tcmTestSubSuite = new TestSuitePojo();
            tcmTestSubSuite.setTitle(se.getName());
            tcmTestSubSuite.setDescription(se.getDescription());
            tcmTestSubSuite.setParentSuiteId(tcmSuiteId);
        }

        /**   CHECKING CASES **/

        List<Case> allTitlesTR = apiTestRailTest.getTestCasesTest(1);

        List<String> allTitlesTcm = aux.getAllTestCasesTitles(tcmData);
        ArrayList<Case> notPresentCases= new ArrayList<>();

        for (Case css : allTitlesTR) {
            boolean contains = allTitlesTcm.contains(css.getTitle());
            if (contains) log.info("Contains title : " + css.getTitle());
            else notPresentCases.add(css);
        }

        log.info("Test Cases titles not present : " + notPresentCases.size() + " / " + allTitlesTR.size());


        /**   CREATING NOT PRESENT CASES **/

        ArrayList<TestCasePojo> tcmTestCases = new ArrayList<>();
        for (Case css : notPresentCases) {
            TcmTestCaseSetup setup = new TcmTestCaseSetup();
            tcmTestCases.add(setup.setTcmTestCaseTest(css, tcmSuiteId));
        }

        /**   POSTING CASES **/
/*
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/test/resources/api/post/postTcmTestCase.json");
        mapper.writeValue(file, tcmTestCases.get(5));
        log.info("File exists : " + file.exists());
        ApiTest api = new ApiTest();
        api.testCreateTestCase();

        java.util.concurrent.TimeUnit.SECONDS.sleep(10);

        mapper.writeValue(new File("src/test/resources/api/post/postTcmTestCase.json"), tcmTestCases.get(7));
        api.testCreateTestCase();

        //mapper.writeValue(new File("src/test/resources/api/post/1.json"), tcmTestSuite);
/*

        for (Case css : notPresentTitles) {
            Experimental experimental = new Experimental();
            TestCasePojo tcss = experimental.setTcmTestCaseTest(css, aux.getSuiteId(names));
            mapper.writeValue(new File("src/test/resources/api/post/postTcmTestCase.json"), tcss);
            ApiTest api = new ApiTest();
            api.testCreateTestCase();
                }
 */

    }
}
