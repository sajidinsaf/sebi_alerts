package com.visibleai.sebi.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.builder.ReportBuilder;
import com.visibleai.sebi.report.job.JobController;
import com.visibleai.sebi.report.printer.ReportPrinter;
import com.visibleai.sebi.report.printer.SebiAlertsReportPrinter;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.validation.util.VisitFrequencyCheckFactory;

public class Orchestrator {

  private List<ReportBuilder> reportBuilders;

  private Properties properties;

  private DateUtil dateUtil;

  private JobController jobController;

  public Orchestrator(Properties properties, DateUtil dateUtil, JobController jobController)
      throws FileNotFoundException {

    reportBuilders = new ReportBuilderFactory(dateUtil, new VisitFrequencyCheckFactory(dateUtil))
        .createReportBuilders(properties);
    this.properties = properties;
    this.dateUtil = dateUtil;
    this.jobController = jobController;

  }

  public ReportGenerationResult generateReports(List<VisitorEntry> visitorEntries) {

    String dateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT);
    Date startDate = new Date();
    Date endDate = null;
    List<File> reportFiles = new ArrayList<File>();
    List<File> reportsNotGenerated = new ArrayList<File>();
    // For each visitor entry do the validations
    for (int i = 0; i < visitorEntries.size(); i++) {

      VisitorEntry visitorEntry = visitorEntries.get(i);
      for (ReportBuilder reportBuilder : reportBuilders) {
        try {
          reportBuilder.build(visitorEntry);
        } catch (RuntimeException e) {
          e.printStackTrace();
          System.out.println("Error while reading visitor entry: [" + visitorEntry + "] with report builder: "
              + reportBuilder.getClass().getName() + " Exception message: " + e.getMessage());
        } catch (Exception e) {
          System.out.println("Failed to process visitor entry " + visitorEntry + " for report builder: "
              + reportBuilder.getName() + " Exception message: " + e.getMessage());
        }
      }

      Date entryDate = dateUtil.parseDate(visitorEntry.getTimeIn(), dateFormat);
      startDate = dateUtil.earlierDate(startDate, entryDate);
      endDate = endDate == null ? entryDate : dateUtil.laterDate(endDate, entryDate);

    }

    ReportPrinter reportPrinter = new SebiAlertsReportPrinter(startDate, endDate, dateUtil);

    String reportDir = buildReportsDir(properties).getAbsolutePath() + System.getProperty("file.separator");

    for (ReportBuilder reportBuilder : reportBuilders) {

      File reportFile = new File(reportBuilder.getClass().getName());
      try {
        Report report = reportBuilder.getReport();

        reportFile = new File(reportDir + report.getFileName());
        PrintStream printStream = new PrintStream(reportFile);
        reportPrinter.print(report, printStream);
        printStream.flush();
        printStream.close();

        reportFiles.add(reportFile);
      } catch (Exception e) {
        reportsNotGenerated.add(reportFile);
      }
    }

    ReportGenerationResult reportGenerationResult = new ReportGenerationResult(reportFiles, reportsNotGenerated);
    return reportGenerationResult;
  }

  private File buildReportsDir(Properties properties2) {
    String reportOutputFilePath = properties.getProperty(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH);

    String jobDir = jobController.getId(properties);

    String reportsDirPath = reportOutputFilePath + System.getProperty("file.separator") + jobDir;

    File file = new File(reportsDirPath);
    file.mkdir();
    return file;
  }

}
