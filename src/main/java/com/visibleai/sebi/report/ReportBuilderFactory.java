package com.visibleai.sebi.report;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.web.multipart.MultipartFile;

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
import com.visibleai.sebi.web.controller.util.ReportSelectUtil;
import com.visibleai.sebi.web.model.RequestReportsForm;

public class ReportBuilderFactory {
  private DateUtil dateUtil;
  private VisitFrequencyCheckFactory visitFrequencyCheckFactory;

  public ReportBuilderFactory(DateUtil dateUtil, VisitFrequencyCheckFactory visitFrequencyCheckFactory) {
    this.dateUtil = dateUtil;
    this.visitFrequencyCheckFactory = visitFrequencyCheckFactory;
  }

  public List<ReportBuilder> createReportBuilders(Properties properties) {

    RequestReportsForm reportsForm = (RequestReportsForm) properties.get(Constants.PROPERTY_REPORT_FORM);

    List<String> reportsToGenerate = new ReportSelectUtil().getReportClassNames(reportsForm);

    System.out.println(reportsToGenerate);

    List<ReportBuilder> reportBuilders = new ArrayList<>();

    if (reportsToGenerate.contains("brokerListCheckReportBuilder")) {
      MultipartFile brokerCompanyListFile = (MultipartFile) properties.get(Constants.PROPERTY_BROKER_LIST_FILE);
      ListCheckValidator brokerCompanyMatchValidator = new CompanyMatchValidator(
          loadListFromFile(brokerCompanyListFile));

      ReportBuilder brokerListCheckReportBuilder = new ListCheckReportBuilder(brokerCompanyMatchValidator,
          "Broker Visitor Report", "BrokerVisitorReport.csv");
      reportBuilders.add(brokerListCheckReportBuilder);
    }

    if (reportsToGenerate.contains("govtListCheckReportBuilder")) {

      MultipartFile govtOrgListFile = (MultipartFile) properties.get(Constants.PROPERTY_GOVT_ORG_LIST_FILE);
      ListCheckValidator govtOrgMatchValidator = new CompanyMatchValidator(loadListFromFile(govtOrgListFile));

      ReportBuilder govtListCheckReportBuilder = new ListCheckReportBuilder(govtOrgMatchValidator,
          "Government Visitor Report", "GovtVisitorReport.csv");

      reportBuilders.add(govtListCheckReportBuilder);
    }

    if (reportsToGenerate.contains("visitorListCheckReportBuilder")) {

      MultipartFile visitorMatchListFile = (MultipartFile) properties.get(Constants.PROPERTY_VISITOR_MATCH_LIST_FILE);
      ListCheckValidator visitorMatchValidator = new VisitorMatchValidator(loadListFromFile(visitorMatchListFile));

      ReportBuilder visitorListCheckReportBuilder = new ListCheckReportBuilder(visitorMatchValidator,
          "Visitor Watch List Report", "VisitorWatchListReport.csv");

      reportBuilders.add(visitorListCheckReportBuilder);
    }

    if (reportsToGenerate.contains("employeesListCheckReportBuilder")) {

      MultipartFile employeeMatchListFile = (MultipartFile) properties.get(Constants.PROPERTY_EMPLOYEE_MATCH_LIST_FILE);
      ListCheckValidator employeeMatchValidator = new EmployeeMatchValidator(loadListFromFile(employeeMatchListFile));

      ReportBuilder employeesListCheckReportBuilder = new ListCheckReportBuilder(employeeMatchValidator,
          "Employees Watch List Report", "EmployeeWatchListReport.csv");

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

  private List<String> loadListFromFile(MultipartFile file) {

    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
      List<String> list = new ArrayList<>();

      String line = null;

      while ((line = bufferedReader.readLine()) != null) {
        list.add(line);
      }
      bufferedReader.close();
      return list;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return new ArrayList<String>();
  }

}
