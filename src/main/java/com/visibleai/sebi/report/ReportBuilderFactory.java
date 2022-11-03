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
import com.visibleai.sebi.validation.CompanyMatchValidator;
import com.visibleai.sebi.validation.EmployeeMatchValidator;
import com.visibleai.sebi.validation.ListCheckValidator;

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

        ListCheckValidator brokerCompanyMatchValidator = new CompanyMatchValidator(
                loadListFromFile(Constants.BROKERS_LIST_FILE));
        ListCheckValidator govtOrgMatchValidator = new CompanyMatchValidator(
                loadListFromFile(Constants.GOVT_ORGS_FILE));
        ListCheckValidator employeeMatchValidator = new EmployeeMatchValidator(
                loadListFromFile(Constants.EMPLOYEE_WATCH_LIST_FILE));

        brokerListCheckReportBuilder = new ListCheckReportBuilder(brokerCompanyMatchValidator, "Broker Visitor Report");
        govtListCheckReportBuilder = new ListCheckReportBuilder(govtOrgMatchValidator, "Government Visitor Report");
        employeesListCheckReportBuilder = new ListCheckReportBuilder(employeeMatchValidator,
                "Employees Watch List Report");

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
