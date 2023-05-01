package com.visibleai.sebi.report.builder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.SebiAlertsReport;
import com.visibleai.sebi.report.TableReportData;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;
import com.visibleai.sebi.validation.util.VisitFrequencyCheck;

public class VisitFrequencyReportBuilder {

    private Logger logger = LoggerFactory.getLogger(VisitFrequencyReportBuilder.class);

    private SebiAlertsReport visitFrequencyReport = null;
    private VisitFrequencyCheck visitFrequencyCheck;
    private Map<String, List<FrequentVisitorDetail>> visitFrequencyCheckMap;
    private int frequencyViolationNumber;
    private int numberOfDays;

    public VisitFrequencyReportBuilder(int numberOfDays, int frequencyViolationNumber, String reportName,
            VisitFrequencyCheck visitFrequencyCheck) {

        this.visitFrequencyCheck = visitFrequencyCheck;

        visitFrequencyReport = new SebiAlertsReport();
        visitFrequencyReport.setFileName(reportName);
        visitFrequencyReport.setTitle(numberOfDays + " Day Check Report");
        visitFrequencyReport.setDate(new Date());
        this.frequencyViolationNumber = frequencyViolationNumber;
        this.numberOfDays = numberOfDays;

    }
//
//  public void build(FrequentVisitorDetail frequentVisitorDetail) {
//
//    // boolean visitFrequencyDayCheckResult =
//    // visitFrequencyCheck.check(frequentVisitorDetail);
//
//
//
//  }

    public List<Report> getReports() {

        logger.debug("Building report for " + numberOfDays + " days frequency check");

        TableReportData visitFrequencyReportData = new TableReportData();

        List<String> header = Arrays.asList("Phone Number", "Name", "Meeting with", "Date(Time In)", "Date(Time Out)",
                "Visitors Company", "Type Of Visitor", "Number of Visits", "Visit Period", "Comments", "Location");
        visitFrequencyReportData.setHeader(header);

        logger.debug("Created report header");

        for (String phoneNumber : visitFrequencyCheckMap.keySet()) {
            List<FrequentVisitorDetail> frequentVisitorDetailList = visitFrequencyCheckMap.get(phoneNumber);
            if (frequentVisitorDetailList.size() < frequencyViolationNumber) {
                continue;
            }

            List<List<FrequentVisitorDetail>> frequentVisitsLists = visitFrequencyCheck
                    .check(frequentVisitorDetailList);

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
                    String visitDatesIn = frequentVisitorDetail.getTimeIn();
                    String visitDatesOut = frequentVisitorDetail.getTimeOut();
                    String visitorCompanies = frequentVisitorDetail.getVisitorCompany();
                    String typeOfVisitor = frequentVisitorDetail.getAccessCardId();
                    String location = frequentVisitorDetail.getLocation();

                    List<String> row = Arrays.asList(phoneNumber, names, meetingsWith, visitDatesIn, visitDatesOut,
                            visitorCompanies, typeOfVisitor, frequencyCount, visitFromTo, comments, location);
                    logger.debug("Created report row for " + numberOfDays + " days frequency check: " + row);

                    visitFrequencyReportData.addRow(row);
                    frequencyCount = "";
                    visitFromTo = "";
                }

            }

        }
        visitFrequencyReport.setReportData(visitFrequencyReportData);
        return Arrays.asList(visitFrequencyReport);

    }

    public String getName() {
        return "VisitFrequencyReportBuilder_" + numberOfDays + "days";
    }

    public void setVisitFrequencyCheckMap(Map<String, List<FrequentVisitorDetail>> visitFrequencyCheckMap) {
        this.visitFrequencyCheckMap = visitFrequencyCheckMap;
    }
}
