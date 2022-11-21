package com.visibleai.sebi.report.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.SebiAlertsReport;
import com.visibleai.sebi.report.TableReportData;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;
import com.visibleai.sebi.validation.util.VisitFrequencyCheck;

public class VisitFrequencyReportBuilder implements ReportBuilder {

  private SebiAlertsReport visitFrequencyReport = null;
  private VisitFrequencyCheck visitFrequencyCheck;
  private Map<String, List<FrequentVisitorDetail>> visitFrequencyCheckMap;
  private int frequencyViolationNumber;

  public VisitFrequencyReportBuilder(int numberOfDays, int frequencyViolationNumber, String reportName,
      VisitFrequencyCheck visitFrequencyCheck) {

    this.visitFrequencyCheck = visitFrequencyCheck;
    visitFrequencyCheckMap = new HashMap<String, List<FrequentVisitorDetail>>();
    visitFrequencyReport = new SebiAlertsReport();
    visitFrequencyReport.setFileName(reportName);
    visitFrequencyReport.setTitle(numberOfDays + " Day Check Report");
    visitFrequencyReport.setDate(new Date());
    this.frequencyViolationNumber = frequencyViolationNumber;

  }

  public void build(VisitorEntry visitorEntry) {
    FrequentVisitorDetail frequentVisitorDetail = new FrequentVisitorDetail(visitorEntry);

    // boolean visitFrequencyDayCheckResult =
    // visitFrequencyCheck.check(frequentVisitorDetail);

    String phoneNumber = frequentVisitorDetail.getVisitorNumber();

    List<FrequentVisitorDetail> frequentVisitorDetailList = visitFrequencyCheckMap.getOrDefault(phoneNumber,
        new ArrayList<FrequentVisitorDetail>());
    frequentVisitorDetailList.add(frequentVisitorDetail);
    visitFrequencyCheckMap.put(phoneNumber, frequentVisitorDetailList);

  }

  public Report getReport() {

    TableReportData visitFrequencyReportData = new TableReportData();

    List<String> header = Arrays.asList("Phone Number", "Name", "Meeting with", "Date", "Visitors Company",
        "Type Of Visitor", "Number of Visits", "Visit Period", "Comments");
    visitFrequencyReportData.setHeader(header);

    for (String phoneNumber : visitFrequencyCheckMap.keySet()) {
      List<FrequentVisitorDetail> frequentVisitorDetailList = visitFrequencyCheckMap.get(phoneNumber);
      if (frequentVisitorDetailList.size() < frequencyViolationNumber) {
        continue;
      }

      List<List<FrequentVisitorDetail>> frequentVisitsLists = visitFrequencyCheck.check(frequentVisitorDetailList);

      for (List<FrequentVisitorDetail> frequentVisitsList : frequentVisitsLists) {
        // "Phone Number", "Name", "Meeting with", "Date", "Visitors Company", "Type Of
        // Visitor", "Comments"
        // "Phone Number", "Name1|Name2|Name3 ", "Meeting with1|Meeting With2|Meeting
        // With3", "Date1|Date2|Date3", "Visitors Company1|Visitors Company2|Visitors
        // Company3", "Type Of Visitor1|Type Of Visitor2|Type Of Visitor3", "Comments"

        String frequencyCount = frequentVisitsList.size() + "";
        String comments = "";
        String visitFromTo = frequentVisitsList.get(0).getTimeIn() + " - "
            + frequentVisitsList.get(frequentVisitsList.size() - 1).getTimeIn();

        for (FrequentVisitorDetail frequentVisitorDetail : frequentVisitsList) {
          String names = frequentVisitorDetail.getVisitorName();
          String meetingsWith = frequentVisitorDetail.getToMeet();
          String visitDates = frequentVisitorDetail.getTimeIn();
          String visitorCompanies = frequentVisitorDetail.getVisitorCompany();
          String typeOfVisitor = frequentVisitorDetail.getAccessCardId();

          List<String> row = Arrays.asList(phoneNumber, names, meetingsWith, visitDates, visitorCompanies,
              typeOfVisitor, frequencyCount, visitFromTo, comments);
          visitFrequencyReportData.addRow(row);
          frequencyCount = "";
          visitFromTo = "";
        }

      }

    }
    visitFrequencyReport.setReportData(visitFrequencyReportData);
    return visitFrequencyReport;

  }

}
