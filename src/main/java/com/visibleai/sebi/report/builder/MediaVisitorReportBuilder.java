package com.visibleai.sebi.report.builder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.SebiAlertsReport;
import com.visibleai.sebi.report.TableReportData;
import com.visibleai.sebi.validation.AccessCardValidator;

public class MediaVisitorReportBuilder implements ReportBuilder {

    private SebiAlertsReport mediaVisitorReport = null;
    private AccessCardValidator mediaVisitorValidator = null;
    private TableReportData mediaVisitorReportData = null;

    public MediaVisitorReportBuilder() {
        mediaVisitorReport = new SebiAlertsReport();
        mediaVisitorReport.setFileName("MediaVisitorReport.csv");
        mediaVisitorReport.setDate(new Date());
        mediaVisitorReport.setTitle("Media Visitor Report");
        mediaVisitorValidator = new AccessCardValidator();
        mediaVisitorReportData = new TableReportData();
        List<String> header = Arrays.asList("Name", "Phone Number", "Meeting", "Date", "Access Card ID", "Comments");
        mediaVisitorReportData.setHeader(header);
    }

    public void build(VisitorEntry visitorEntry) {
        if (!mediaVisitorValidator.isMedia(visitorEntry.getAccessCardId())) {
            return; // as this is not a media visitor record we will ignore and exit from this
                    // method
        }
        String name = visitorEntry.getVisitorName();
        String phoneNumber = visitorEntry.getVisitorNumber();
        String meeting = visitorEntry.getToMeet();
        String date = visitorEntry.getTimeIn();
        String accessCardID = visitorEntry.getAccessCardId();
        String comments = "";
        List<String> row = Arrays.asList(name, phoneNumber, meeting, date, accessCardID, comments);
        mediaVisitorReportData.addRow(row);
    }

    public Report getReport() {
        mediaVisitorReport.setReportData(mediaVisitorReportData);
        return mediaVisitorReport;
    }

}
