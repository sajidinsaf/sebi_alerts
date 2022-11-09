package com.visibleai.sebi.report.printer;

import static com.visibleai.sebi.model.Constants.REPORT_DATE_FORMAT;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    outputStream.println(
        "Title: " + report.getTitle() + ",,,,Report Date: " + dateUtil.asString(report.getDate(), REPORT_DATE_FORMAT));

    ReportData reportData = report.getReportData();
    List<List<String>> rows = reportData.getRows();

    if (rows != null && rows.size() > 0) {
      outputStream.println("Start Date: " + dateUtil.asString(startDate, REPORT_DATE_FORMAT) + ",,End Date: "
          + dateUtil.asString(endDate, REPORT_DATE_FORMAT));
    } else {
      outputStream.println("Note: No qualifying entries found for this report");
    }

    outputStream.println("");
    outputStream.println("");
    List<String> header = reportData.getHeader();

    String headerString = getAsCsvString(header);

    outputStream.println(headerString);

    for (List<String> row : rows) {
      String rowString = getAsCsvString(row);
      outputStream.println(rowString);
    }
  }

  private String getAsCsvString(List<String> data) {
    // As we are writing to a CSV file, check and replace any comma in the data with
    // a semi-colon
    List<String> dataList = data.stream().map(s -> s != null ? s.replaceAll(",", ";") : "")
        .collect(Collectors.toList());
    String dataString = dataList.toString();
    dataString = dataString.substring(1);
    dataString = dataString.substring(0, dataString.length() - 1);
    return dataString;
  }

}
