package com.visibleai.sebi.report;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.ListCheckReportBuilder;
import com.visibleai.sebi.report.builder.MediaVisitorReportBuilder;
import com.visibleai.sebi.report.builder.OutOfOfficeHoursReportBuilder;
import com.visibleai.sebi.report.builder.ReportBuilder;
import com.visibleai.sebi.report.builder.VisitFrequencyReportBuilder;

public class ReportBuilderFactory {

    public List<ReportBuilder> createReportBuilders() {
        ReportBuilder brokerListCheckReportBuilder;
        ReportBuilder govtListCheckReportBuilder;
        ReportBuilder employeesListCheckReportBuilder;
        ReportBuilder mediaVisitorReportBuilder;
        ReportBuilder outOfOfficeHoursReportBuilder;
        ReportBuilder weekVisitFrequencyReportBuilder;
        ReportBuilder twoWeekVisitFrequencyReportBuilder;
        ReportBuilder monthvisitFrequencyReportBuilder;

        brokerListCheckReportBuilder = new ListCheckReportBuilder(loadListFromFile(Constants.BROKERS_LIST_FILE),
                "Broker Visitor Report");
        govtListCheckReportBuilder = new ListCheckReportBuilder(loadListFromFile(Constants.GOVT_ORGS_FILE),
                "Government Visitor Report");
        employeesListCheckReportBuilder = new ListCheckReportBuilder(
                loadListFromFile(Constants.EMPLOYEE_WATCH_LIST_FILE), "Employees Watch List Report");
        mediaVisitorReportBuilder = new MediaVisitorReportBuilder();
        outOfOfficeHoursReportBuilder = new OutOfOfficeHoursReportBuilder();
        weekVisitFrequencyReportBuilder = new VisitFrequencyReportBuilder(7, 3);
        twoWeekVisitFrequencyReportBuilder = new VisitFrequencyReportBuilder(14, 6);
        monthvisitFrequencyReportBuilder = new VisitFrequencyReportBuilder(30, 9);

        List<ReportBuilder> reportBuilders = Arrays.asList(brokerListCheckReportBuilder, govtListCheckReportBuilder,
                employeesListCheckReportBuilder, mediaVisitorReportBuilder, outOfOfficeHoursReportBuilder,
                weekVisitFrequencyReportBuilder, twoWeekVisitFrequencyReportBuilder, monthvisitFrequencyReportBuilder);
        return reportBuilders;
    }

    private List<String> loadListFromFile(String fileName) {
        try {
            List<String> list = Files.readAllLines(new File(fileName).toPath(), Charset.defaultCharset());
            return list;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
}
