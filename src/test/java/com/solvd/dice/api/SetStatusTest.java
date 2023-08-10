package com.solvd.dice.api;

import com.solvd.dice.api.dataSuite.launch.TestCases;
import com.solvd.dice.api.service.DataSuiteService;
import com.solvd.dice.api.service.TcmService;
import com.solvd.dice.api.tcmResult.TcmCaseResult;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SetStatusTest {

    @Test
    public void main() throws IOException {
        DataSuiteService dataSuiteService = new DataSuiteService();
        TcmService tcmService = new TcmService();

        int launchId = 17677;
        String testRunId = "72";
        List<TestCases> casesResults = dataSuiteService.getLaunchResults(launchId);
        List<TcmCaseResult> allTcmResults = new ArrayList<>();

        for (TestCases cr : casesResults) {
            log.info("TestExecutionId : " + cr.getTestExecutionId() + ". TcmType : " + cr.getTcmType() + ". Result status : " + cr.getResultStatus());
            TcmCaseResult tcmCaseResult = new TcmCaseResult();
            tcmCaseResult.setAutomationLaunchId(launchId);
            tcmCaseResult.setTestExecutionId(Math.toIntExact(cr.getTestExecutionId()));
            tcmCaseResult.setTrCaseId(cr.getTestCaseId());
            tcmCaseResult.setTcmCaseId(String.valueOf(tcmService.getIdFromFile(cr.getTestCaseId())));
            tcmCaseResult.setStatusId(tcmCaseResult.getStatusIdByName(cr.getResultStatus()));
            allTcmResults.add(tcmCaseResult);
            log.warn(tcmCaseResult.toString());
        }
        List<Integer> groupedExec = tcmService.groupExecutions(allTcmResults);
        System.out.println("");
        for (Integer exec : groupedExec) {
            List<String> groupedCases = tcmService.groupCasesResultByExecutionId(allTcmResults, exec);
            TcmCaseResult tcmCaseResult = tcmService.getCaseResultById(allTcmResults, groupedCases.get(0));
            log.warn("Exec Id " + exec + " : " + groupedCases + " " + tcmCaseResult.getStatusId() + " " + tcmCaseResult.getAutomationLaunchId());
            tcmService.setTcmStatus(testRunId, groupedCases, tcmCaseResult);
        }
    }
}
