package com.visibleai.sebi.report.printer;

import java.util.List;

import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.ReportData;

public class SebiAlertsReportPrinter implements ReportPrinter {

    @Override
    public void print(Report report) {
        System.out.println("Title: " + report.getTitle());
        System.out.println("Date: " + report.getDate());
        ReportData reportData = report.getReportData();
        List<String> header = reportData.getHeader();
        System.out.println(header);
        List<List<String>> rows = reportData.getRows();
        for (List<String> row : rows) {
            System.out.println(row);
        }
    }

}
