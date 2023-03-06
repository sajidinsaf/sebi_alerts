package com.visibleai.sebi.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.visibleai.sebi.db.VisitorEntryRowMapper;
import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.builder.ReportBuilder;
import com.visibleai.sebi.report.job.JobController;
import com.visibleai.sebi.report.printer.ReportPrinter;
import com.visibleai.sebi.report.printer.SebiAlertsReportPrinter;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.validation.util.VisitFrequencyCheckFactory;

public class DatabaseOrchestrator implements RowCallbackHandler {

  private List<ReportBuilder> reportBuilders;

  private Properties properties;

  private DateUtil dateUtil;

  private JobController jobController;

  private Date startDate = new Date();
  private Date endDate = null;

  int rowNum = 0;
  private VisitorEntryRowMapper visitorEntryRowMapper;

  public DatabaseOrchestrator(Properties properties, DateUtil dateUtil, JobController jobController)
      throws FileNotFoundException {

    reportBuilders = new ReportBuilderFactory(dateUtil, new VisitFrequencyCheckFactory(dateUtil))
        .createReportBuilders(properties);
    this.properties = properties;
    this.dateUtil = dateUtil;
    this.jobController = jobController;
    visitorEntryRowMapper = new VisitorEntryRowMapper();
  }

  private boolean buildReports(VisitorEntry visitorEntry) {

    String dateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT);

    System.out.println("Generating Reports");
    // For each visitor entry do the validations

    for (ReportBuilder reportBuilder : reportBuilders) {
      try {
        System.out
            .println("Processing visitor entry" + visitorEntry + " for report builder: " + reportBuilder.getName());
        reportBuilder.build(visitorEntry);
      } catch (RuntimeException e) {
        e.printStackTrace();
        System.out.println("Error while reading visitor entry: [" + visitorEntry + "] with report builder: "
            + reportBuilder.getClass().getName() + " Exception message: " + e.getMessage());
        return false;
      } catch (Exception e) {
        System.out.println("Failed to process visitor entry " + visitorEntry + " for report builder: "
            + reportBuilder.getName() + " Exception message: " + e.getMessage());
        return false;
      }
    }

    Date entryDate = dateUtil.parseDate(visitorEntry.getTimeIn(), dateFormat);
    startDate = dateUtil.earlierDate(startDate, entryDate);
    endDate = endDate == null ? entryDate : dateUtil.laterDate(endDate, entryDate);
    return true;

  }

  public ReportGenerationResult finish() {

    List<File> reportFiles = new ArrayList<File>();
    List<File> reportsNotGenerated = new ArrayList<File>();

    ReportPrinter reportPrinter = new SebiAlertsReportPrinter(startDate, endDate, dateUtil);

    String reportDir = buildReportsDir(properties).getAbsolutePath() + System.getProperty("file.separator");

    for (ReportBuilder reportBuilder : reportBuilders) {

      File reportFile = new File(reportBuilder.getClass().getName());

      List<Report> reports = reportBuilder.getReports();

      for (Report report : reports) {
        try {
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
    }

    ReportGenerationResult reportGenerationResult = new ReportGenerationResult(reportFiles, reportsNotGenerated);
    rowNum = 0;
    return reportGenerationResult;
  }

  private File buildReportsDir(Properties properties2) {
    String reportOutputFilePath = properties.getProperty(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH);

    String jobDir = jobController.getId(properties);

    String reportsJobDirPath = reportOutputFilePath + System.getProperty("file.separator") + jobDir;

    properties.put(Constants.PROPERTY_REPORT_JOB_ID, jobDir);

    File file = new File(reportsJobDirPath);
    file.mkdir();
    return file;
  }

  @Override
  public void processRow(ResultSet rs) throws SQLException {
    VisitorEntry visitorEntry = visitorEntryRowMapper.mapRow(rs, ++rowNum);
    buildReports(visitorEntry);
  }

}
