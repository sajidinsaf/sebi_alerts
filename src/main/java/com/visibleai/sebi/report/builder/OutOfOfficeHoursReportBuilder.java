package com.visibleai.sebi.report.builder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.SebiAlertsReport;
import com.visibleai.sebi.report.TableReportData;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.validation.VisitorTimeValidator;

public class OutOfOfficeHoursReportBuilder implements ReportBuilder {

  private SebiAlertsReport outOfOfficeHoursReport = null;
  private VisitorTimeValidator outOfOfficeHoursValidator = null;
  private TableReportData outOfOfficeHoursReportData = null;

  public OutOfOfficeHoursReportBuilder(Properties properties, DateUtil dateUtil) {
    outOfOfficeHoursReport = new SebiAlertsReport();
    outOfOfficeHoursReport.setFileName("OutOfOfficeHoursReport.csv");
    outOfOfficeHoursReport.setDate(new Date());
    outOfOfficeHoursReport.setTitle("Out Of Office Hours Report");
    outOfOfficeHoursValidator = new VisitorTimeValidator(properties, dateUtil);
    outOfOfficeHoursReportData = new TableReportData();
    List<String> header = Arrays.asList("Name", "Phone Number", "Visitor Company", "Date(Time In)", "Date(Time Out)",
        "Meeting", "Type Of Visitor", "Comments");
    outOfOfficeHoursReportData.setHeader(header);
  }

  public void build(VisitorEntry visitorEntry) {

    if (!outOfOfficeHoursValidator.isOutOfWorkingHours(visitorEntry.getTimeIn())) {
      return;
    }

    String name = visitorEntry.getVisitorName();
    String phoneNumber = visitorEntry.getVisitorNumber();
    String visitorCompany = visitorEntry.getVisitorCompany();
    String dateIn = visitorEntry.getTimeIn();
    String dateOut = visitorEntry.getTimeOut();
    String meeting = visitorEntry.getToMeet();
    String accessCardId = visitorEntry.getAccessCardId();
    String comments = "";
    List<String> row = Arrays.asList(name, phoneNumber, visitorCompany, dateIn, dateOut, meeting, accessCardId,
        comments);
    outOfOfficeHoursReportData.addRow(row);

  }

  public List<Report> getReports() {

    outOfOfficeHoursReport.setReportData(outOfOfficeHoursReportData);
    return Arrays.asList(outOfOfficeHoursReport);
  }

  public String getName() {
    return "OutOfOfficeHoursReportBuilder";
  }
}
