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
        List<String> header = Arrays.asList("Name", "Phone Number", "Meeting", "Date(Time In)", "Date(Time Out)",
                "Access Card ID", "Comments", "Location");
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
        String dateIn = visitorEntry.getTimeIn();
        String dateOut = visitorEntry.getTimeOut();
        String accessCardID = visitorEntry.getAccessCardId();
        String comments = "";
        String location = visitorEntry.getLocation();
        List<String> row = Arrays.asList(name, phoneNumber, meeting, dateIn, dateOut, accessCardID, comments, location);
        mediaVisitorReportData.addRow(row);
    }

    public List<Report> getReports() {
        mediaVisitorReport.setReportData(mediaVisitorReportData);
        return Arrays.asList(mediaVisitorReport);
    }

    public String getName() {
        return "MediaVisitorReportBuilder";
    }

}
