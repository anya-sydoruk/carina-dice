package com.solvd.dice.api.service;

import java.io.IOException;
import java.util.*;

import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;
import com.codepine.api.testrail.model.CaseType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.dice.api.dataSuite.Tabs.Field;
import com.solvd.dice.api.tcmCustomField.CustomField;
import com.solvd.dice.api.dataSuite.TestSuites.TestCase;
import com.solvd.dice.api.dataSuite.TestSuites.TestSuite;
import com.solvd.dice.api.tcmCustomField.Option;
import com.solvd.dice.api.tcmCustomField.Value;
import com.solvd.dice.api.tcmTestCasePojo.TestCasePojo;
import com.solvd.dice.api.tcmTestCasePojo.TestSuitePojo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcmService {

    public static HashMap<Integer, Section> sectionsMapTR = new HashMap<>();
    public static HashMap<Integer, Suite> suitesMapTR = new HashMap<>();
    public static HashMap<Integer, CaseType> caseTypesMapTR = new HashMap<>();
    public static String valueAsString = "";

    public CustomField createCaseTypeField(List<CaseType> caseTypes, int tabId) {
        CustomField customField = new CustomField();

        customField.setName("Case Type");
        customField.setDataType("DROPDOWN");
        customField.setTabId(tabId);
        Value value = new Value();
        value.setHasIcon(false);
        List<Option> options = new ArrayList<>();

        for (CaseType ct : caseTypes) {
            Option option = new Option();
            option.setName(ct.getName());
            option.setIsDefault(ct.isDefault());
            option.setRelativePosition(caseTypes.indexOf(ct));
            options.add(option);
        }
        value.setOptions(options);
        customField.setValue(value);

        ObjectMapper mapper = new ObjectMapper();
        try {
            valueAsString = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ApiTcm api = new ApiTcm();
        api.testCreateCustomField(customField);
        return customField;
    }

    public CustomField createTestRailIdField(int tabId) {
        CustomField customField = new CustomField();
        customField.setName("TestRail ID");
        customField.setDataType("STRING");
        customField.setTabId(tabId);
        valueAsString = "{}";
        ApiTcm api = new ApiTcm();
        api.testCreateCustomField(customField);
        return customField;
    }

    public void createFields(Field[] fields, List<CaseType> caseTypes, int tabId){
        boolean isCaseTypePresent = false;
        boolean isTrIdPresent = false;
        for (Field fi : fields) {
            if (fi.getName().contains("Case Type")) {
                isCaseTypePresent = true;
                log.info("Case Type field is present");
            }
            if (fi.getName().contains("TestRail ID")) {
                isTrIdPresent = true;
                log.info("TestRail Id field is present");
            }
        }
        for (CaseType ct : caseTypes) {
            TcmService.caseTypesMapTR.put(ct.getId(), ct);
        }
        if (!isCaseTypePresent) createCaseTypeField(caseTypes, tabId);
        if (!isTrIdPresent) createTestRailIdField(tabId);
    }

    public ArrayList<Suite> getNotPresentSuites(List<String> suiteNamesTcm, List<Suite> suitesTR) {
        ArrayList<Suite> notPresentSuites = new ArrayList<>();
        for (Suite tss : suitesTR) {
            suitesMapTR.put(tss.getId(), tss);
            boolean contains = suiteNamesTcm.contains(tss.getName());
            if (!contains) notPresentSuites.add(tss);
        }
        log.warn("Suites TR : " + suitesTR.size() + ". Suites TCM : " + suiteNamesTcm.size() + ". Not present: " + notPresentSuites.size());
        return notPresentSuites;
    }

    public ArrayList<Section> getNotPresentSections(ArrayList<TestSuite> sectionsTcm, List<Section> sectionsTR) {
        DataSuiteService dataSuiteService = new DataSuiteService();
        List<String> sectionNamesTcm = dataSuiteService.getSubSuiteTitles(sectionsTcm);

        ArrayList<Section> notPresentSectionTitles = new ArrayList<>();

        for (Section se : sectionsTR) {
            sectionsMapTR.put(se.getId(), se);
        }
        for (Section se : sectionsTR) {
            String parentName;
            if (sectionsMapTR.get(se.getParentId()) != null)
                parentName = sectionsMapTR.get(se.getParentId()).getName();
            else parentName = suitesMapTR.get(se.getSuiteId()).getName();

            boolean contains = false;
            if (sectionNamesTcm.contains(se.getName())) {
                for (TestSuite sui : sectionsTcm) {
                    boolean containsName = se.getName().contains(sui.getTitle());
                    boolean containsParent = parentName.contains(DataSuiteService.sectionParentsMapTcm.get(sui.getId()));
                    if (containsName && containsParent) contains = true;
                }
                if (!contains) notPresentSectionTitles.add(se);
            } else notPresentSectionTitles.add(se);
        }
        log.warn("Sections TR : " + sectionsTR.size() + ". Sections TCM : " + sectionNamesTcm.size() + ". Not present : " + notPresentSectionTitles.size());
        return notPresentSectionTitles;
    }

    public ArrayList<Case> getNotPresentCases(List<TestCase> testCasesTcm, List<Case> testCasesTR) {
        DataSuiteService dataSuiteService = new DataSuiteService();
        List<String> caseTitlesTcm = dataSuiteService.getTestCaseTitles(testCasesTcm);

        ArrayList<Case> notPresentCases = new ArrayList<>();

        for (Case css : testCasesTR) {

            boolean contains = false;
            if (caseTitlesTcm.contains(css.getTitle())) {
                for (TestCase title : testCasesTcm) {
                    boolean containsName = css.getTitle().contains(title.getTitle());
                    boolean containsParent = sectionsMapTR.get(css.getSectionId()).getName().contains(DataSuiteService.casesParentsMapTcm.get(title.getId()));
                    if (containsName && containsParent) contains = true;
                }
                if (!contains) notPresentCases.add(css);
            } else notPresentCases.add(css);
        }
        log.warn("Test Cases TR : " + testCasesTR.size() + ". Test Cases TCM : " + caseTitlesTcm.size() + ". Not present : " + notPresentCases.size());
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

    public void createNotPresentSubSuites(List<Section> notPresentSections, int tcmSuiteId) throws IOException {
        DataSuiteService dataSuiteService = new DataSuiteService();
        TestSuite[] tcmData = dataSuiteService.getTestData(); //updating data from TCM if there are any changes
        dataSuiteService.getSubSuites(tcmData, tcmSuiteId); //updating sectionParentsMapTcm
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
                        int tcmParentId = dataSuiteService.getSectionId(tcmData, tcmSuiteId, sectionsMapTR.get(se.getParentId()));
                        tcmTestSubSuite.setParentSuiteId(tcmParentId);
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
        ApiTcm api = new ApiTcm();
        for (Case css : notPresentCases) {
            int tcmSectionId = dataSuiteService.getSectionId(tcmData, tcmSuiteId, sectionsMapTR.get(css.getSectionId()));
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
