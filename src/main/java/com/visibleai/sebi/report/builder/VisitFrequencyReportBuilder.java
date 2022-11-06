package com.visibleai.sebi.report.builder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.SebiAlertsReport;
import com.visibleai.sebi.report.TableReportData;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;
import com.visibleai.sebi.validation.VisitFrequencyCheck;

public class VisitFrequencyReportBuilder implements ReportBuilder {

    private SebiAlertsReport visitFrequencyReport = null;
    private VisitFrequencyCheck visitFrequencyCheck;
    private Map<String, List<FrequentVisitorDetail>> visitFrequencyCheckMap;
    private int frequencyViolationNumber;

    public VisitFrequencyReportBuilder(int numberOfDays, int frequencyViolationNumber, Properties properties,
            String reportName) {
        visitFrequencyCheck = new VisitFrequencyCheck(numberOfDays, properties);
        visitFrequencyCheckMap = new HashMap<String, List<FrequentVisitorDetail>>();
        visitFrequencyReport = new SebiAlertsReport();
        visitFrequencyReport.setFileName(reportName);
        visitFrequencyReport.setTitle(numberOfDays + " Day Check Report");
        visitFrequencyReport.setDate(new Date());
        this.frequencyViolationNumber = frequencyViolationNumber;

    }

    public void build(VisitorEntry visitorEntry) {
        FrequentVisitorDetail frequentVisitorDetail = new FrequentVisitorDetail(visitorEntry);

        try {
            boolean visitFrequencyDayCheckResult = visitFrequencyCheck.check(frequentVisitorDetail);

            String phoneNumber = frequentVisitorDetail.getVisitorNumber();

            if (visitFrequencyDayCheckResult) {
                List<FrequentVisitorDetail> frequentVisitorDetailList = visitFrequencyCheckMap.getOrDefault(phoneNumber,
                        new ArrayList<FrequentVisitorDetail>());
                frequentVisitorDetailList.add(frequentVisitorDetail);
                visitFrequencyCheckMap.put(phoneNumber, frequentVisitorDetailList);
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Report getReport() {

        TableReportData visitFrequencyReportData = new TableReportData();

        List<String> header = Arrays.asList("Phone Number", "Name", "Meeting with", "Date", "Visitors Company",
                "Type Of Visitor", "Comments");
        visitFrequencyReportData.setHeader(header);

        for (String phoneNumber : visitFrequencyCheckMap.keySet()) {
            List<FrequentVisitorDetail> frequentVisitorDetailList = visitFrequencyCheckMap.get(phoneNumber);
            if (frequentVisitorDetailList.size() >= frequencyViolationNumber) {
                // "Phone Number", "Name", "Meeting with", "Date", "Visitors Company", "Type Of
                // Visitor", "Comments"
                // "Phone Number", "Name1|Name2|Name3 ", "Meeting with1|Meeting With2|Meeting
                // With3", "Date1|Date2|Date3", "Visitors Company1|Visitors Company2|Visitors
                // Company3", "Type Of Visitor1|Type Of Visitor2|Type Of Visitor3", "Comments"

                String names = "";
                String meetingsWith = "";
                String visitDates = "";
                String visitorCompanies = "";
                String typeOfVisitor = "";
                String comments = "";
                for (FrequentVisitorDetail frequentVisitorDetail : frequentVisitorDetailList) {
                    names = names + frequentVisitorDetail.getVisitorName() + "|";
                    meetingsWith = meetingsWith + frequentVisitorDetail.getToMeet() + "|";
                    visitDates = visitDates + frequentVisitorDetail.getTimeIn() + "|";
                    visitorCompanies = visitorCompanies + frequentVisitorDetail.getVisitorCompany() + "|";
                    typeOfVisitor = typeOfVisitor + frequentVisitorDetail.getAccessCardId() + "|";
                }

                List<String> row = Arrays.asList(phoneNumber, names, meetingsWith, visitDates, visitorCompanies,
                        typeOfVisitor, comments);
                visitFrequencyReportData.addRow(row);
            }

        }
        visitFrequencyReport.setReportData(visitFrequencyReportData);
        return visitFrequencyReport;

    }

}
