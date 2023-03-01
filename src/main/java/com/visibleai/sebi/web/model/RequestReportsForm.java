package com.visibleai.sebi.web.model;

import java.io.Serializable;
import java.sql.Date;

public class RequestReportsForm implements Serializable {

    private static final long serialVersionUID = -6696870351358503492L;
    private Date startDate;
    private Date endDate;

    private boolean generateAllReports;
    private boolean generateMediaVisitorReport;
    private boolean generateBrokerReport;
    private boolean generateEmployeeWatchReport;
    private boolean generateGovtReport;
    private boolean generateVisitorWatchListReport;
    private boolean generateFreqencyCheck7;
    private boolean generateFreqencyCheck14;
    private boolean generateFreqencyCheck30;
    private boolean generateOutOfOfficeHoursReport;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isGenerateAllReports() {
        return generateAllReports;
    }

    public void setGenerateAllReports(boolean generateAllReports) {
        this.generateAllReports = generateAllReports;
    }

    public boolean isGenerateBrokerReport() {
        return generateBrokerReport;
    }

    public void setGenerateBrokerReport(boolean generateBrokerReport) {
        this.generateBrokerReport = generateBrokerReport;
    }

    public boolean isGenerateEmployeeWatchReport() {
        return generateEmployeeWatchReport;
    }

    public void setGenerateEmployeeWatchReport(boolean generateEmployeeWatchReport) {
        this.generateEmployeeWatchReport = generateEmployeeWatchReport;
    }

    public boolean isGenerateGovtReport() {
        return generateGovtReport;
    }

    public void setGenerateGovtReport(boolean generateGovtReport) {
        this.generateGovtReport = generateGovtReport;
    }

    public boolean isGenerateVisitorWatchListReport() {
        return generateVisitorWatchListReport;
    }

    public void setGenerateVisitorWatchListReport(boolean generateVisitorWatchListReport) {
        this.generateVisitorWatchListReport = generateVisitorWatchListReport;
    }

    public boolean isGenerateFreqencyCheck7() {
        return generateFreqencyCheck7;
    }

    public void setGenerateFreqencyCheck7(boolean generateFreqencyCheck7) {
        this.generateFreqencyCheck7 = generateFreqencyCheck7;
    }

    public boolean isGenerateFreqencyCheck14() {
        return generateFreqencyCheck14;
    }

    public void setGenerateFreqencyCheck14(boolean generateFreqencyCheck14) {
        this.generateFreqencyCheck14 = generateFreqencyCheck14;
    }

    public boolean isGenerateFreqencyCheck30() {
        return generateFreqencyCheck30;
    }

    public void setGenerateFreqencyCheck30(boolean generateFreqencyCheck30) {
        this.generateFreqencyCheck30 = generateFreqencyCheck30;
    }

    public boolean isGenerateOutOfOfficeHoursReport() {
        return generateOutOfOfficeHoursReport;
    }

    public void setGenerateOutOfOfficeHoursReport(boolean generateOutOfOfficeHoursReport) {
        this.generateOutOfOfficeHoursReport = generateOutOfOfficeHoursReport;
    }

    public boolean isGenerateMediaVisitorReport() {
        return generateMediaVisitorReport;
    }

    public void setGenerateMediaVisitorReport(boolean generateMediaVisitorReport) {
        this.generateMediaVisitorReport = generateMediaVisitorReport;
    }

    @Override
    public String toString() {
        return "RequestReportsForm [startDate=" + startDate + ", endDate=" + endDate + ", generateAllReports="
                + generateAllReports + ", generateMediaVisitorReport=" + generateMediaVisitorReport
                + ", generateBrokerReport=" + generateBrokerReport + ", generateEmployeeWatchReport="
                + generateEmployeeWatchReport + ", generateGovtReport=" + generateGovtReport
                + ", generateVisitorWatchListReport=" + generateVisitorWatchListReport + ", generateFreqencyCheck7="
                + generateFreqencyCheck7 + ", generateFreqencyCheck14=" + generateFreqencyCheck14
                + ", generateFreqencyCheck30=" + generateFreqencyCheck30 + ", generateOutOfOfficeHoursReport="
                + generateOutOfOfficeHoursReport + "]";
    }

}