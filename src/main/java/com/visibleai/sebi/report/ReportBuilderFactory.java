package com.visibleai.sebi.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.ListCheckReportBuilder;
import com.visibleai.sebi.report.builder.MediaVisitorReportBuilder;
import com.visibleai.sebi.report.builder.OutOfOfficeHoursReportBuilder;
import com.visibleai.sebi.report.builder.ReportBuilder;
import com.visibleai.sebi.report.builder.VisitFrequencyReportBuilder;
import com.visibleai.sebi.report.fileloader.FileLoader;
import com.visibleai.sebi.report.fileloader.LocalFileLoader;
import com.visibleai.sebi.report.fileloader.MultipartFileLoader;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.validation.CompanyMatchValidator;
import com.visibleai.sebi.validation.EmployeeMatchValidator;
import com.visibleai.sebi.validation.ListCheckValidator;
import com.visibleai.sebi.validation.VisitorMatchValidator;
import com.visibleai.sebi.validation.util.VisitFrequencyCheckFactory;
import com.visibleai.sebi.web.controller.util.ReportSelectUtil;
import com.visibleai.sebi.web.model.RequestReportsForm;

public class ReportBuilderFactory {
  private DateUtil dateUtil;
  private VisitFrequencyCheckFactory visitFrequencyCheckFactory;

  private Map<String, FileLoader> fileLoaders;

  public ReportBuilderFactory(DateUtil dateUtil, VisitFrequencyCheckFactory visitFrequencyCheckFactory) {
    this.dateUtil = dateUtil;
    this.visitFrequencyCheckFactory = visitFrequencyCheckFactory;
    fileLoaders = new HashMap<>();
    fileLoaders.put("web", new MultipartFileLoader());
    fileLoaders.put("standalone", new LocalFileLoader());
  }

  public List<ReportBuilder> createReportBuilders(Properties properties) {

    RequestReportsForm reportsForm = (RequestReportsForm) properties.get(Constants.PROPERTY_REPORT_FORM);

    List<String> reportsToGenerate = new ReportSelectUtil().getReportClassNames(reportsForm);

    // System.out.println(reportsToGenerate);

    List<ReportBuilder> reportBuilders = new ArrayList<>();

    FileLoader fileLoader = fileLoaders.get(properties.getProperty(Constants.PROPERTY_SERVER_TYPE));

    if (reportsToGenerate.contains("brokerListCheckReportBuilder")) {

      ListCheckValidator brokerCompanyMatchValidator = new CompanyMatchValidator(
          fileLoader.loadFileLines(properties, Constants.PROPERTY_BROKER_LIST_FILE));

      ReportBuilder brokerListCheckReportBuilder = new ListCheckReportBuilder(brokerCompanyMatchValidator,
          "Broker Visitor Report", "BrokerVisitorReport.csv", "BrokerVisitorReportBuilder");
      reportBuilders.add(brokerListCheckReportBuilder);
    }

    if (reportsToGenerate.contains("govtListCheckReportBuilder")) {

      ListCheckValidator govtOrgMatchValidator = new CompanyMatchValidator(
          fileLoader.loadFileLines(properties, Constants.PROPERTY_GOVT_ORG_LIST_FILE));

      ReportBuilder govtListCheckReportBuilder = new ListCheckReportBuilder(govtOrgMatchValidator,
          "Government Visitor Report", "GovtVisitorReport.csv", "GovernmentVisitorReportBuilder");

      reportBuilders.add(govtListCheckReportBuilder);
    }

    if (reportsToGenerate.contains("visitorListCheckReportBuilder")) {

      ListCheckValidator visitorMatchValidator = new VisitorMatchValidator(
          fileLoader.loadFileLines(properties, Constants.PROPERTY_VISITOR_MATCH_LIST_FILE));

      ReportBuilder visitorListCheckReportBuilder = new ListCheckReportBuilder(visitorMatchValidator,
          "Visitor Watch List Report", "VisitorWatchListReport.csv", "VisitorWatchListReportBuilder");

      reportBuilders.add(visitorListCheckReportBuilder);
    }

    if (reportsToGenerate.contains("employeesListCheckReportBuilder")) {
      ListCheckValidator employeeMatchValidator = new EmployeeMatchValidator(
          fileLoader.loadFileLines(properties, Constants.PROPERTY_EMPLOYEE_MATCH_LIST_FILE));

      ReportBuilder employeesListCheckReportBuilder = new ListCheckReportBuilder(employeeMatchValidator,
          "Employees Watch List Report", "EmployeeWatchListReport.csv", "EmployeesWatchListReportBuilde");

      reportBuilders.add(employeesListCheckReportBuilder);
    }

    if (reportsToGenerate.contains("mediaVisitorReportBuilder")) {
      ReportBuilder mediaVisitorReportBuilder = new MediaVisitorReportBuilder();
      reportBuilders.add(mediaVisitorReportBuilder);
    }

    if (reportsToGenerate.contains("outOfOfficeHoursReportBuilder")) {
      ReportBuilder outOfOfficeHoursReportBuilder = new OutOfOfficeHoursReportBuilder(properties, dateUtil);
      reportBuilders.add(outOfOfficeHoursReportBuilder);
    }

    if (reportsToGenerate.contains("weekVisitFrequencyReportBuilder")) {
      String fileNamePrefix = "VisitorFrequencyCheckReport-";
      String fileNameSuffix = "_days.csv";
      ReportBuilder weekVisitFrequencyReportBuilder = new VisitFrequencyReportBuilder(7, 3,
          fileNamePrefix + "7" + fileNameSuffix, visitFrequencyCheckFactory.getVisitFrequencyCheck(properties, 7, 3));
      reportBuilders.add(weekVisitFrequencyReportBuilder);
    }

    if (reportsToGenerate.contains("twoWeekVisitFrequencyReportBuilder")) {
      String fileNamePrefix = "VisitorFrequencyCheckReport-";
      String fileNameSuffix = "_days.csv";
      ReportBuilder twoWeekVisitFrequencyReportBuilder = new VisitFrequencyReportBuilder(14, 6,
          fileNamePrefix + "14" + fileNameSuffix, visitFrequencyCheckFactory.getVisitFrequencyCheck(properties, 14, 6));
      reportBuilders.add(twoWeekVisitFrequencyReportBuilder);
    }

    if (reportsToGenerate.contains("monthVisitFrequencyReportBuilder")) {
      String fileNamePrefix = "VisitorFrequencyCheckReport-";
      String fileNameSuffix = "_days.csv";
      ReportBuilder monthVisitFrequencyReportBuilder = new VisitFrequencyReportBuilder(30, 9,
          fileNamePrefix + "30" + fileNameSuffix, visitFrequencyCheckFactory.getVisitFrequencyCheck(properties, 30, 9));
      reportBuilders.add(monthVisitFrequencyReportBuilder);
    }

    return reportBuilders;
  }

}
