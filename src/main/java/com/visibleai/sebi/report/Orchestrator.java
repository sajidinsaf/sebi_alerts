package com.visibleai.sebi.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.builder.ReportBuilder;
import com.visibleai.sebi.report.printer.ReportPrinter;
import com.visibleai.sebi.report.printer.SebiAlertsReportPrinter;
import com.visibleai.sebi.util.DateUtil;

public class Orchestrator {

  private List<ReportBuilder> reportBuilders;

  private Properties properties;

  private DateUtil dateUtil;

  public Orchestrator(Properties properties, DateUtil dateUtil) throws FileNotFoundException {

    reportBuilders = new ReportBuilderFactory(dateUtil).createReportBuilders(properties);
    this.properties = properties;
    this.dateUtil = dateUtil;

  }

  public void generateReports() throws IOException {

    String fileName = null;

    fileName = properties.getProperty(Constants.PROPERTY_VISITOR_ENTRY_FILE);

    // create an instance of the file
    File file = new File(fileName);

    // create a reader for this file
    BufferedReader reader = new BufferedReader(new FileReader(file));

    // Parse the file using the apache CSV library
    CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

    // Get the list of CSV records
    List<CSVRecord> csvRecords = parser.getRecords();

    String dateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT);
    Date startDate = new Date();
    Date endDate = null;

    // For each CSV record do the validations
    for (int i = 0; i < csvRecords.size(); i++) {
      CSVRecord csvRecord = csvRecords.get(i);

      VisitorEntry visitorEntry = new VisitorEntry(csvRecord);

      for (ReportBuilder reportBuilder : reportBuilders) {
        try {
          reportBuilder.build(visitorEntry);
        } catch (RuntimeException e) {
          System.out.println("Error while reading visitor entry: [" + visitorEntry + "] with report builder: "
              + reportBuilder.getClass().getName() + " Exception message: " + e);
        }
      }

      Date entryDate = dateUtil.parseDate(visitorEntry.getTimeIn(), dateFormat);
      startDate = dateUtil.earlierDate(startDate, entryDate);
      endDate = endDate == null ? entryDate : dateUtil.laterDate(endDate, entryDate);

    }

    ReportPrinter reportPrinter = new SebiAlertsReportPrinter(startDate, endDate, dateUtil);
    String reportGenerationDate = dateUtil.asString(new Date(), "MMM-dd-yyyy");
    for (ReportBuilder reportBuilder : reportBuilders) {

      Report report = reportBuilder.getReport();
      String reportOutputFilePath = properties.getProperty(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH);

      File reportFile = new File(reportOutputFilePath + System.getProperty("file.separator") + reportGenerationDate
          + "_" + report.getFileName());
      PrintStream printStream = new PrintStream(reportFile);
      reportPrinter.print(report, printStream);
      printStream.flush();
      printStream.close();
    }

  }

}
