package com.visibleai.sebi.report.printer;

import static com.visibleai.sebi.model.Constants.REPORT_DATE_FORMAT;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.ReportData;
import com.visibleai.sebi.util.DateUtil;

public class SebiAlertsReportPrinter implements ReportPrinter {

    private Date startDate;
    private Date endDate;
    private DateUtil dateUtil;

    public SebiAlertsReportPrinter(Date startDate, Date endDate, DateUtil dateUtil) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateUtil = dateUtil;
    }

    @Override
    public void print(Report report, PrintStream outputStream) {
        outputStream.println("Title: " + report.getTitle() + ",,,,Report Date: "
                + dateUtil.asString(report.getDate(), REPORT_DATE_FORMAT));
        outputStream.println("Start Date: " + dateUtil.asString(startDate, REPORT_DATE_FORMAT) + ",,End Date: "
                + dateUtil.asString(endDate, REPORT_DATE_FORMAT));
        outputStream.println("");
        outputStream.println("");
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
