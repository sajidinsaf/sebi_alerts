package com.visibleai.sebi.report;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.ListCheckReportBuilder;
import com.visibleai.sebi.report.builder.MediaVisitorReportBuilder;
import com.visibleai.sebi.report.builder.OutOfOfficeHoursReportBuilder;
import com.visibleai.sebi.report.builder.ReportBuilder;
import com.visibleai.sebi.report.builder.VisitFrequencyReportBuilder;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.validation.CompanyMatchValidator;
import com.visibleai.sebi.validation.EmployeeMatchValidator;
import com.visibleai.sebi.validation.ListCheckValidator;
import com.visibleai.sebi.validation.VisitorMatchValidator;
import com.visibleai.sebi.validation.util.VisitFrequencyCheckFactory;

public class ReportBuilderFactory {
  private DateUtil dateUtil;
  private VisitFrequencyCheckFactory visitFrequencyCheckFactory;

  public ReportBuilderFactory(DateUtil dateUtil, VisitFrequencyCheckFactory visitFrequencyCheckFactory) {
    this.dateUtil = dateUtil;
    this.visitFrequencyCheckFactory = visitFrequencyCheckFactory;
  }

  public List<ReportBuilder> createReportBuilders(Properties properties) {
    ReportBuilder brokerListCheckReportBuilder;
    ReportBuilder govtListCheckReportBuilder;
    ReportBuilder visitorListCheckReportBuilder;
    ReportBuilder employeesListCheckReportBuilder;
    ReportBuilder mediaVisitorReportBuilder;
    ReportBuilder outOfOfficeHoursReportBuilder;
    ReportBuilder weekVisitFrequencyReportBuilder;
    ReportBuilder twoWeekVisitFrequencyReportBuilder;
    ReportBuilder monthvisitFrequencyReportBuilder;

    String brokerCompanyListFile = properties.getProperty(Constants.PROPERTY_BROKER_LIST_FILE);
    String govtOrgListFile = properties.getProperty(Constants.PROPERTY_GOVT_ORG_LIST_FILE);
    String employeeMatchListFile = properties.getProperty(Constants.PROPERTY_EMPLOYEE_MATCH_LIST_FILE);
    String visitorMatchListFile = properties.getProperty(Constants.PROPERTY_VISITOR_MATCH_LIST_FILE);

    ListCheckValidator brokerCompanyMatchValidator = new CompanyMatchValidator(loadListFromFile(brokerCompanyListFile));
    ListCheckValidator govtOrgMatchValidator = new CompanyMatchValidator(loadListFromFile(govtOrgListFile));
    ListCheckValidator employeeMatchValidator = new EmployeeMatchValidator(loadListFromFile(employeeMatchListFile));
    ListCheckValidator visitorMatchValidator = new VisitorMatchValidator(loadListFromFile(visitorMatchListFile));

    brokerListCheckReportBuilder = new ListCheckReportBuilder(brokerCompanyMatchValidator, "Broker Visitor Report",
        "BrokerVisitorReport.csv");
    govtListCheckReportBuilder = new ListCheckReportBuilder(govtOrgMatchValidator, "Government Visitor Report",
        "GovtVisitorReport.csv");
    employeesListCheckReportBuilder = new ListCheckReportBuilder(employeeMatchValidator, "Employees Watch List Report",
        "EmployeeWatchListReport.csv");

    visitorListCheckReportBuilder = new ListCheckReportBuilder(visitorMatchValidator, "Visitor Watch List Report",
        "VisitorWatchListReport.csv");

    mediaVisitorReportBuilder = new MediaVisitorReportBuilder();
    outOfOfficeHoursReportBuilder = new OutOfOfficeHoursReportBuilder(properties, dateUtil);
    String fileNamePrefix = "VisitorFrequencyCheckReport-";
    String fileNameSuffix = "_days.csv";

    weekVisitFrequencyReportBuilder = new VisitFrequencyReportBuilder(7, 3, fileNamePrefix + "7" + fileNameSuffix,
        visitFrequencyCheckFactory.getVisitFrequencyCheck(properties, 7, 3));
    twoWeekVisitFrequencyReportBuilder = new VisitFrequencyReportBuilder(14, 6, fileNamePrefix + "14" + fileNameSuffix,
        visitFrequencyCheckFactory.getVisitFrequencyCheck(properties, 14, 6));
    monthvisitFrequencyReportBuilder = new VisitFrequencyReportBuilder(30, 9, fileNamePrefix + "30" + fileNameSuffix,
        visitFrequencyCheckFactory.getVisitFrequencyCheck(properties, 30, 9));

    List<ReportBuilder> reportBuilders = Arrays.asList(brokerListCheckReportBuilder, govtListCheckReportBuilder,
        employeesListCheckReportBuilder, visitorListCheckReportBuilder, mediaVisitorReportBuilder,
        outOfOfficeHoursReportBuilder, weekVisitFrequencyReportBuilder, twoWeekVisitFrequencyReportBuilder,
        monthvisitFrequencyReportBuilder);
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
