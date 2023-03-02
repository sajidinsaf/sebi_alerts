package com.visibleai.sebi.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.visibleai.sebi.db.VisitorEntryDatabaseReader;
import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Orchestrator;
import com.visibleai.sebi.report.ReportGenerationResult;
import com.visibleai.sebi.test.MailSenderMain;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.web.model.RequestReportsForm;

@Controller
public class ThymeLeafController {

  @Autowired
  private VisitorEntryDatabaseReader visitorEntryDatabaseReader;

  @RequestMapping(value = "/index")
  public String index() {
    return "index";
  }

  @RequestMapping(value = "/generateReports")
  public String getVisitorEntries(@ModelAttribute RequestReportsForm requestReportsForm, Model model,
      @Value("${vams.db.query}") String query, @Value("${jdbc.driver.class}") String driver,
      @Value("${vams.db.password}") String password, @Value("${vams.db.user}") String user,
      @Value("${vams.db.url}") String url, @Value("${broker.list.file}") String brokerList,
      @Value("${govt.org.list.file}") String governmentOrgList, @Value("${visitor.list.file}") String visitorList,
      @Value("${employee.match.list.file}") String employeeMatchList,
      @Value("${entry.datetime.format}") String visitorEntryDateTimeFormat,
      @Value("${report.out.file.path}") String reportOutputFilePath, @Value("${mail.smtp.server}") String mailServer,
      @Value("${mail.smtp.port}") String mailPort, @Value("${mail.ssl.port}") String mailSSLPort,
      @Value("${mail.smtp.auth}") String mailAuth, @Value("${mail.start.tls.enable}") String mailTLSEnable,
      @Value("${mail.username}") String mailUsername, @Value("${mail.password}") String mailPassword,
      @Value("${mail.from.address}") String mailFromAddress, @Value("${mail.to.addresses}") String mailToAddress,
      @Value("${mail.subject}") String mailSubject, @Value("${mail.text}") String mailText,
      @RequestParam(name = "generateBrokerReportListFile", required = false) MultipartFile brokerListFile,
      @RequestParam(name = "generateEmployeeWatchReportListFile", required = false) MultipartFile employeeWatchListFile,
      @RequestParam(name = "generateGovtReportListFile", required = false) MultipartFile governmentListFile,
      @RequestParam(name = "generateVisitorWatchListReportListFile", required = false) MultipartFile visitorWatchListFile)
      throws IOException {

    Properties properties = new Properties();
    properties.put(Constants.PROPERTY_VAMS_DB_QUERY, query);
    properties.put(Constants.PROPERTY_JDBC_DRIVER_CLASS, driver);
    properties.put(Constants.PROPERTY_VAMS_DB_PASSWORD, password);
    properties.put(Constants.PROPERTY_VAMS_DB_USER, user);
    properties.put(Constants.PROPERTY_VAMS_DB_URL, url);
    properties.put(Constants.PROPERTY_REPORT_FORM, requestReportsForm);
    properties.put(Constants.PROPERTY_BROKER_LIST_FILE, brokerList);
    properties.put(Constants.PROPERTY_GOVT_ORG_LIST_FILE, governmentOrgList);
    properties.put(Constants.PROPERTY_VISITOR_MATCH_LIST_FILE, visitorList);
    properties.put(Constants.PROPERTY_EMPLOYEE_MATCH_LIST_FILE, employeeMatchList);
    properties.put(Constants.PROPERTY_ENTRY_DATETIME_FORMAT, visitorEntryDateTimeFormat);
    properties.put(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH, reportOutputFilePath);

    System.out.println(brokerListFile);
    List<VisitorEntry> visitorEntries = visitorEntryDatabaseReader.getVisitorEntries(properties);
    model.addAttribute("visitorEntries", visitorEntries);
    Orchestrator orchestrator = new Orchestrator(properties, new DateUtil());

    ReportGenerationResult reportGenerationResult = orchestrator.generateReports(visitorEntries);

    model.addAttribute("reportFiles", reportGenerationResult.getGeneratedReports());
    model.addAttribute("reportsNotGenerated", reportGenerationResult.getFailedReports());

    MailSenderMain mailSenderMain = new MailSenderMain();
    properties.put(Constants.PROPERTY_MAIL_SMTP_SERVER, mailServer);
    properties.put(Constants.PROPERTY_MAIL_SMTP_PORT, mailPort);
    properties.put(Constants.PROPERTY_MAIL_SSL_PORT, mailSSLPort);
    properties.put(Constants.PROPERTY_MAIL_SMTP_AUTH, "true");
    properties.put(Constants.PROPERTY_MAIL_START_TLS_ENABLE, "true");
    properties.put(Constants.PROPERTY_MAIL_SERVER_USERNAME, mailUsername);
    properties.put(Constants.PROPERTY_MAIL_SERVER_PASSWORD, mailPassword);
    properties.put(Constants.PROPERTY_MAIL_FROM_ADDRESS, mailFromAddress);
    properties.put(Constants.PROPERTY_MAIL_TO_ADDRESSES, mailToAddress);

    properties.put(Constants.PROPERTY_MAIL_SUBJECT, mailSubject);
    properties.put(Constants.PROPERTY_MAIL_TEXT, mailText);

    mailSenderMain.sendMail(properties);

    return "generateReports";
  }
}
