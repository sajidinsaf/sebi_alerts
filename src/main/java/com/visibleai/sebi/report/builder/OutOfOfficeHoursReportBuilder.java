package com.visibleai.sebi.report.builder;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.SebiAlertsReport;
import com.visibleai.sebi.report.TableReportData;
import com.visibleai.sebi.validation.VisitorTimeValidator;

public class OutOfOfficeHoursReportBuilder implements ReportBuilder {

    private SebiAlertsReport outOfOfficeHoursReport = null;
    private VisitorTimeValidator outOfOfficeHoursValidator = null;
    private TableReportData outOfOfficeHoursReportData = null;
    private Properties properties;

    public OutOfOfficeHoursReportBuilder(Properties properties) {
        outOfOfficeHoursReport = new SebiAlertsReport();
        outOfOfficeHoursReport.setFileName("OutOfOfficeHoursReport.csv");
        outOfOfficeHoursReport.setDate(new Date());
        outOfOfficeHoursReport.setTitle("Out Of Office Hours Report");
        outOfOfficeHoursValidator = new VisitorTimeValidator(properties);
        outOfOfficeHoursReportData = new TableReportData();
        List<String> header = Arrays.asList("Name", "Phone Number", "Visitor Company", "Date", "Meeting",
                "Type Of Visitor", "Comments");
        outOfOfficeHoursReportData.setHeader(header);
        this.properties = properties;
    }

    public void build(VisitorEntry visitorEntry) {
        try {
            if (!outOfOfficeHoursValidator.isOutOfWorkingHours(visitorEntry.getTimeIn())) {
                return;
            }
        } catch (ParseException e) {
            System.out.println("Invalid Date Time Format. Expected format "
                    + properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT) + ". Visitor entry: "
                    + visitorEntry);
            return;
        }
        String name = visitorEntry.getVisitorName();
        String phoneNumber = visitorEntry.getVisitorNumber();
        String visitorCompany = visitorEntry.getVisitorCompany();
        String date = visitorEntry.getTimeIn();
        String meeting = visitorEntry.getToMeet();
        String accessCardId = visitorEntry.getAccessCardId();
        String comments = "";
        List<String> row = Arrays.asList(name, phoneNumber, visitorCompany, date, meeting, accessCardId, comments);
        outOfOfficeHoursReportData.addRow(row);

    }

    public Report getReport() {

        outOfOfficeHoursReport.setReportData(outOfOfficeHoursReportData);
        return outOfOfficeHoursReport;
    }

}
