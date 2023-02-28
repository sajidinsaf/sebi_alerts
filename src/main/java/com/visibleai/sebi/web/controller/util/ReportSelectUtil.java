package com.visibleai.sebi.web.controller.util;

import java.util.ArrayList;
import java.util.List;

import com.visibleai.sebi.web.model.RequestReportsForm;

public class ReportSelectUtil {

    private static final List<String> reportClasses;

    static {
        reportClasses = new ArrayList<>();
        reportClasses.add("brokerListCheckReportBuilder");
        reportClasses.add("govtListCheckReportBuilder");
        reportClasses.add("visitorListCheckReportBuilder");
        reportClasses.add("employeesListCheckReportBuilder");
        reportClasses.add("mediaVisitorReportBuilder");
        reportClasses.add("outOfOfficeHoursReportBuilder");
        reportClasses.add("weekVisitFrequencyReportBuilder");
        reportClasses.add("twoWeekVisitFrequencyReportBuilder");
        reportClasses.add("monthVisitFrequencyReportBuilder");

    }

    public List<String> getReportClassNames(RequestReportsForm reportsForm) {
        List<String> reports = new ArrayList<>(reportClasses);

        if (reportsForm.isGenerateAllReports()) {
            return reports;
        }
        if (!reportsForm.isGenerateBrokerReport()) {
            reports.remove("brokerListCheckReportBuilder");
        }
        if (!reportsForm.isGenerateGovtReport()) {
            reports.remove("govtListCheckReportBuilder");
        }
        if (!reportsForm.isGenerateVisitorWatchListReport()) {
            reports.remove("visitorListCheckReportBuilder");
        }
        if (!reportsForm.isGenerateEmployeeWatchReport()) {
            reports.remove("employeesListCheckReportBuilder");
        }
        if (!reportsForm.isGenerateMediaVisitorReport()) {
            reports.remove("mediaVisitorReportBuilder");
        }
        if (!reportsForm.isGenerateOutOfOfficeHoursReport()) {
            reports.remove("outOfOfficeHoursReportBuilder");
        }
        if (!reportsForm.isGenerateFreqencyCheck7()) {
            reports.remove("weekVisitFrequencyReportBuilder");
        }
        if (!reportsForm.isGenerateFreqencyCheck14()) {
            reports.remove("twoWeekVisitFrequencyReportBuilder");
        }
        if (!reportsForm.isGenerateFreqencyCheck30()) {
            reports.remove("monthVisitFrequencyReportBuilder");
        }
        return reports;
    }

}
