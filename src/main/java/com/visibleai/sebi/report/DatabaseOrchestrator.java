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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private Logger logger = LoggerFactory.getLogger(DatabaseOrchestrator.class);

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
    this.properties = properties;
    this.dateUtil = dateUtil;
    this.jobController = jobController;
    visitorEntryRowMapper = new VisitorEntryRowMapper(properties);

    buildReportsDir(properties);
    reportBuilders = new ReportBuilderFactory(dateUtil, new VisitFrequencyCheckFactory(dateUtil))
        .createReportBuilders(properties);

  }

  private boolean buildReports(VisitorEntry visitorEntry) {

    String dateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT);

    // For each visitor entry do the validations

    for (ReportBuilder reportBuilder : reportBuilders) {
      try {
        if (logger.isDebugEnabled()) {
          logger.debug("Processing visitor entry" + visitorEntry + " for report builder: " + reportBuilder.getName());
        }
        reportBuilder.build(visitorEntry);
      } catch (RuntimeException e) {
        logger.error("Error while reading visitor entry: [" + visitorEntry + "] with report builder: "
            + reportBuilder.getClass().getName(), e);
        return false;
      } catch (Exception e) {
        logger.debug(
            "Failed to process visitor entry " + visitorEntry + " for report builder: " + reportBuilder.getName(), e);
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
    String reportDir = properties.getProperty(Constants.PROPERTY_REPORT_JOB_DIR);
    ReportPrinter reportPrinter = new SebiAlertsReportPrinter(startDate, endDate, dateUtil);

    for (ReportBuilder reportBuilder : reportBuilders) {

      File reportFile = new File(reportBuilder.getClass().getName());

      List<Report> reports = reportBuilder.getReports();

      for (Report report : reports) {
        try {
          reportFile = new File(reportDir + System.getProperty("file.separator") + report.getFileName());
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

  private File buildReportsDir(Properties properties) {
    String reportOutputFilePath = properties.getProperty(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH);

    String jobDir = jobController.getId(properties);

    String reportsJobDirPath = reportOutputFilePath + System.getProperty("file.separator") + jobDir;

    properties.put(Constants.PROPERTY_REPORT_JOB_ID, jobDir);

    File file = new File(reportsJobDirPath);
    file.mkdir();
    properties.put(Constants.PROPERTY_REPORT_JOB_DIR, reportsJobDirPath);
    return file;
  }

  @Override
  public void processRow(ResultSet rs) throws SQLException {
    VisitorEntry visitorEntry = visitorEntryRowMapper.mapRow(rs, ++rowNum);
    // logger.trace("Processing row number: " + rowNum);
    buildReports(visitorEntry);
  }

}
