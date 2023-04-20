package com.visibleai.sebi.web.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.visibleai.sebi.mail.MailSenderMain;
import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.DatabaseOrchestrator;
import com.visibleai.sebi.report.ReportGenerationResult;
import com.visibleai.sebi.report.job.JobController;
import com.visibleai.sebi.test.db.TestDataLoader;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.web.model.RequestReportsForm;

@Controller
@RequestMapping("/api/vea")
public class ThymeLeafController {

  private Logger logger = LoggerFactory.getLogger(ThymeLeafController.class);

  @Value("${run.mode}")
  String runMode;

  @Autowired
  private JdbcTemplate vamsJdbcTemplate;

  @Autowired
  private TestDataLoader testDataLoader;

  @Autowired
  private JobController jobController;

  @Autowired
  private DateUtil dateUtil;

  @Value("${vams.db.query}")
  private String query;
  @Value("${jdbc.driver.class}")
  private String driver;
  @Value("${vams.db.password}")
  private String password;
  @Value("${vams.db.user}")
  private String user;
  @Value("${vams.db.url}")
  private String url;
  @Value("${vams.db.time.offset.in.hours}")
  private String vamsDbTimeOffsetInHours;
  @Value("${broker.list.file}")
  private String brokerList;
  @Value("${govt.org.list.file}")
  private String governmentOrgList;
  @Value("${visitor.list.file}")
  private String visitorList;
  @Value("${employee.match.list.file}")
  private String employeeMatchList;
  @Value("${entry.datetime.format}")
  private String visitorEntryDateTimeFormat;
  @Value("${report.out.file.path}")
  private String reportOutputFilePath;
  @Value("${mail.smtp.server}")
  private String mailServer;
  @Value("${mail.smtp.port}")
  private String mailPort;
  @Value("${mail.ssl.port}")
  private String mailSSLPort;
  @Value("${mail.smtp.auth}")
  private String mailAuth;
  @Value("${mail.start.tls.enable}")
  private String mailTLSEnable;
  @Value("${mail.username}")
  private String mailUsername;
  @Value("${mail.password}")
  private String mailPassword;
  @Value("${mail.from.address}")
  private String mailFromAddress;
  @Value("${mail.to.addresses}")
  private String mailToAddress;
  @Value("${mail.subject}")
  private String mailSubject;
  @Value("${mail.text}")
  private String mailText;
  @Value("${server.type}")
  private String serverType;

  // Periodic alert attributes
  @Value("${periodic.alert.query}")
  private String periodAlertVisitorEntriesQuery;

  @RequestMapping(value = "/")
  public String index() {
    return "index";
  }

  @GetMapping(value = "/reports")
  public String reports(Model model) {
    setupTestData(false);
    model.addAttribute("requestReportsForm", new RequestReportsForm());
    return "reportsForm";
  }

  private Semaphore semaphore = new Semaphore(1);

  @RequestMapping(value = "/generateReports")
  public String generateReports(@ModelAttribute RequestReportsForm requestReportsForm, Model model,

      @RequestParam(name = "generateBrokerReportListFile", required = false) MultipartFile brokerListFile,
      @RequestParam(name = "generateEmployeeWatchReportListFile", required = false) MultipartFile employeeWatchListFile,
      @RequestParam(name = "generateGovtReportListFile", required = false) MultipartFile governmentListFile,
      @RequestParam(name = "generateVisitorWatchListReportListFile", required = false) MultipartFile visitorWatchListFile)
      throws IOException {

    aquireToken("on-demand");

    try {
      Properties properties = setUpProperties(requestReportsForm, Constants.ALERT_JOB_TYPE_ONDEMAND);

      setFiles(properties, brokerListFile, employeeWatchListFile, governmentListFile, visitorWatchListFile);

      DatabaseOrchestrator orchestrator = new DatabaseOrchestrator(properties, dateUtil, jobController);
      vamsJdbcTemplate.query(query, new PreparedStatementSetter() {
        public void setValues(PreparedStatement pstmt) throws SQLException {
          pstmt.setTimestamp(1, dateUtil.toTimeStamp(requestReportsForm.getStartDate()));
          pstmt.setTimestamp(2, dateUtil.endOfday(requestReportsForm.getEndDate())); // set the date to the end of the
                                                                                     // day
        }
      }, orchestrator);

      ReportGenerationResult reportGenerationResult = orchestrator.finish();

      properties.put(Constants.OBJECT_KEY_REPORT_GENERATION_RESULT, reportGenerationResult);

      model.addAttribute("reportFiles", reportGenerationResult.getGeneratedReports());
      model.addAttribute("reportsNotGenerated", reportGenerationResult.getFailedReports());

      MailSenderMain mailSenderMain = new MailSenderMain();

      mailSenderMain.sendMail(properties);
    } finally {
      releaseToken("on-demand");
    }
    return "generateReports";
  }

  @RequestMapping(value = "/periodicAlerts")
  public String periodicAlerts(Model model) throws IOException {

    aquireToken("periodic");
    try {
      setupTestData(true);

      RequestReportsForm requestReportsForm = new RequestReportsForm();
      requestReportsForm.setGenerateAllReports(true);

      long today = System.currentTimeMillis();
      long thirtyDaysBeforeToday = today - Constants.ONE_MONTH_IN_MILLISECONDS;

      requestReportsForm.setStartDate(new Date(thirtyDaysBeforeToday));
      requestReportsForm.setEndDate(new Date(today));
      Properties properties = setUpProperties(requestReportsForm, Constants.ALERT_JOB_TYPE_PERIODIC);

      File brokerListFile = new File(brokerList);
      File employeeWatchListFile = new File(employeeMatchList);
      File governmentListFile = new File(governmentOrgList);
      File visitorWatchListFile = new File(visitorList);

      setFiles(properties, brokerListFile, employeeWatchListFile, governmentListFile, visitorWatchListFile);

      properties.put(Constants.PROPERTY_SERVER_TYPE, Constants.PROPERTY_SERVER_TYPE_LOCAL);

      DatabaseOrchestrator orchestrator = new DatabaseOrchestrator(properties, dateUtil, jobController);

      vamsJdbcTemplate.query(periodAlertVisitorEntriesQuery, orchestrator);

      ReportGenerationResult reportGenerationResult = orchestrator.finish();

      properties.put(Constants.OBJECT_KEY_REPORT_GENERATION_RESULT, reportGenerationResult);

      model.addAttribute("reportFiles", reportGenerationResult.getGeneratedReports());
      model.addAttribute("reportsNotGenerated", reportGenerationResult.getFailedReports());

      requestReportsForm.setEmailTo(mailToAddress);

      MailSenderMain mailSenderMain = new MailSenderMain();

      mailSenderMain.sendMail(properties);
    } finally {
      releaseToken("periodic");
    }

    return "generateReports";
  }

  private void setFiles(Properties properties, Object brokerListFile, Object employeeWatchListFile,
      Object governmentListFile, Object visitorWatchListFile) {

    if (brokerListFile != null) {
      properties.put(Constants.PROPERTY_BROKER_LIST_FILE, brokerListFile);
    }

    if (employeeWatchListFile != null) {
      properties.put(Constants.PROPERTY_EMPLOYEE_MATCH_LIST_FILE, employeeWatchListFile);
    }

    if (governmentListFile != null) {
      properties.put(Constants.PROPERTY_GOVT_ORG_LIST_FILE, governmentListFile);
    }

    if (visitorWatchListFile != null) {
      properties.put(Constants.PROPERTY_VISITOR_MATCH_LIST_FILE, visitorWatchListFile);
    }

  }

  private Properties setUpProperties(RequestReportsForm requestReportsForm, String alertJobType) {
    Properties properties = new Properties();
    properties.put(Constants.ALERT_JOB_TYPE, alertJobType);
    properties.put(Constants.PROPERTY_VAMS_DB_QUERY, query);
    properties.put(Constants.PROPERTY_JDBC_DRIVER_CLASS, driver);
    properties.put(Constants.PROPERTY_VAMS_DB_PASSWORD, password);
    properties.put(Constants.PROPERTY_VAMS_DB_USER, user);
    properties.put(Constants.PROPERTY_VAMS_DB_URL, url);
    properties.put(Constants.PROPERTY_VAMS_DB_TIME_OFFSET_IN_HOURS, vamsDbTimeOffsetInHours);

    properties.put(Constants.PROPERTY_BROKER_LIST_FILE, brokerList);
    properties.put(Constants.PROPERTY_GOVT_ORG_LIST_FILE, governmentOrgList);
    properties.put(Constants.PROPERTY_VISITOR_MATCH_LIST_FILE, visitorList);
    properties.put(Constants.PROPERTY_EMPLOYEE_MATCH_LIST_FILE, employeeMatchList);
    properties.put(Constants.PROPERTY_ENTRY_DATETIME_FORMAT, visitorEntryDateTimeFormat);
    properties.put(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH, reportOutputFilePath);
    properties.put(Constants.PROPERTY_SERVER_TYPE, serverType);

    // mail properties
    properties.put(Constants.PROPERTY_MAIL_SMTP_SERVER, mailServer);
    properties.put(Constants.PROPERTY_MAIL_SMTP_PORT, mailPort);
    properties.put(Constants.PROPERTY_MAIL_SSL_PORT, mailSSLPort);
    properties.put(Constants.PROPERTY_MAIL_SMTP_AUTH, mailAuth);
    properties.put(Constants.PROPERTY_MAIL_START_TLS_ENABLE, mailTLSEnable);
    properties.put(Constants.PROPERTY_MAIL_SERVER_USERNAME, mailUsername);
    properties.put(Constants.PROPERTY_MAIL_SERVER_PASSWORD, mailPassword);
    properties.put(Constants.PROPERTY_MAIL_FROM_ADDRESS, mailFromAddress);
    properties.put(Constants.PROPERTY_MAIL_TO_ADDRESSES, mailToAddress);

    properties.put(Constants.PROPERTY_MAIL_SUBJECT, mailSubject);
    properties.put(Constants.PROPERTY_MAIL_TEXT, mailText);
    properties.put(Constants.PROPERTY_REPORT_FORM, requestReportsForm);
    return properties;
  }

  private void aquireToken(String process) {
    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Acquiring semaphore for " + process + " reports");
      }
      semaphore.acquire();
      if (logger.isDebugEnabled()) {
        logger.debug("Semaphore acquired for " + process + " reports");
      }
    } catch (InterruptedException e) {
      logger.warn("Semaphone acquistion interrupted. There may be a conflict between periodic and on-demand reports");
    }
  }

  private void releaseToken(String process) {
    logger.debug("Releasing semaphore for " + process + " reports");
    semaphore.release();
    logger.debug("Semaphore released for " + process + " reports");

  }

  private void setupTestData(boolean isPeriodic) {
    if (runMode != null && runMode.equals("test")) {
      testDataLoader.loadTestData(vamsJdbcTemplate);
      if (isPeriodic) {
        testDataLoader.setupPeriodicData(vamsJdbcTemplate);
      }
    }

  }
}
