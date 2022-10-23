package com.visibleai.sebi.report;

import java.util.Date;

public class SebiAlertsReport implements Report {

    private String title = null;

    private Date date = null;

    private ReportData reportData = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String titleName) {
        title = titleName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date dateOfReport) {
        date = dateOfReport;
    }

    public ReportData getReportData() {
        return reportData;
    }

    public void setReportData(ReportData dataInReport) {
        reportData = dataInReport;
    }

}
