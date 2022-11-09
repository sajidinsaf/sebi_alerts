package com.visibleai.sebi.report.printer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.visibleai.sebi.report.SebiAlertsReport;
import com.visibleai.sebi.report.TableReportData;
import com.visibleai.sebi.util.DateUtil;

public class SebiAlertsReportPrinterTest {

  @Test
  public void testSebiAlertsReportPrinter() {
    SebiAlertsReportPrinter sebiAlertsReportPrinter = new SebiAlertsReportPrinter(new Date(), new Date(),
        new DateUtil());
    SebiAlertsReport report = new SebiAlertsReport();
    report.setTitle("Blibbity Blobbaty Blabbaty");
    Date date = new Date();
    report.setDate(date);
    TableReportData reportData = new TableReportData();
    List<String> header = Arrays.asList("A", "B", "C");
    List<String> row = Arrays.asList("a", "B,b", "C,c,cc");
    reportData.setHeader(header);
    reportData.addRow(row);
    report.setReportData(reportData);
    sebiAlertsReportPrinter.print(report, System.out);

  }

}
