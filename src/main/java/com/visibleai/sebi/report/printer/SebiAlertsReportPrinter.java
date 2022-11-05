package com.visibleai.sebi.report.printer;

import java.io.PrintStream;
import java.util.List;

import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.ReportData;

public class SebiAlertsReportPrinter implements ReportPrinter {

    private PrintStream outputStream;

    public SebiAlertsReportPrinter(PrintStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void print(Report report) {
        outputStream.println("Title: " + report.getTitle());
        outputStream.println("Date: " + report.getDate());

        ReportData reportData = report.getReportData();
        List<String> header = reportData.getHeader();

        String headerString = getAsCsvString(header);

        outputStream.println(headerString);

        List<List<String>> rows = reportData.getRows();
        for (List<String> row : rows) {
            String rowString = getAsCsvString(row);
            outputStream.println(rowString);
        }
    }

    private String getAsCsvString(List<String> data) {
        String dataString = data.toString();
        dataString = dataString.substring(1);
        dataString = dataString.substring(0, dataString.length() - 1);
        return dataString;
    }

}
