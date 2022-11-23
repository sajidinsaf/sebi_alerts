package com.visibleai.sebi.report.builder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.SebiAlertsReport;
import com.visibleai.sebi.report.TableReportData;
import com.visibleai.sebi.validation.ListCheckValidator;

public class ListCheckReportBuilder implements ReportBuilder {

    private ListCheckValidator listCheckValidator;

    private SebiAlertsReport listCheckReport;
    private TableReportData listCheckReportData;

    public ListCheckReportBuilder(ListCheckValidator listCheckValidator, String reportTitle, String reportName) {

        this.listCheckValidator = listCheckValidator;

        listCheckReport = new SebiAlertsReport();
        listCheckReport.setFileName(reportName);
        listCheckReport.setDate(new Date());
        listCheckReport.setTitle(reportTitle);
        listCheckReportData = new TableReportData();
        List<String> header = Arrays.asList("Visitor Company", "Name", "Phone Number", "Meeting with", "Date(Time In)",
                "Date(Time Out)", "Type of visitor", "Comments");
        listCheckReportData.setHeader(header);

    }

    public void build(VisitorEntry visitorEntry) {
        String visitorCompany = visitorEntry.getVisitorCompany();

        if (!listCheckValidator.isInList(visitorEntry)) {
            return;
        }
        String name = visitorEntry.getVisitorName();
        String phoneNumber = visitorEntry.getVisitorNumber();
        String meeting = visitorEntry.getToMeet();
        String dateIn = visitorEntry.getTimeIn();
        String dateOut = visitorEntry.getTimeOut();
        String typeOfVisitor = visitorEntry.getAccessCardId();
        String comments = "";
        List<String> row = Arrays.asList(visitorCompany, name, phoneNumber, meeting, dateIn, dateOut, typeOfVisitor,
                comments);
        listCheckReportData.addRow(row);
    }

    public Report getReport() {
        listCheckReport.setReportData(listCheckReportData);
        return listCheckReport;
    }

}
