package com.visibleai.sebi.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.ReportGenerationResult;
import com.visibleai.sebi.web.model.RequestReportsForm;

public class MailSenderMain {

  private static final Logger logger = LoggerFactory.getLogger(MailSenderMain.class);

  public MailSenderMain() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) throws Exception {

    Properties properties = new Properties();
    String configFilePath = Constants.DEFAULT_CONFIG_FILE_PATH;

    if (args != null && args.length > 0) {
      configFilePath = args[0];
    }

    FileReader fileReader = new FileReader(configFilePath);

    logger.debug("Reading email properties from: " + configFilePath);

    properties.load(fileReader);

    MailSenderMain mailSenderMain = new MailSenderMain();
    mailSenderMain.sendMail(properties);
  }

  public void sendMail(Properties config) {
    logger.debug("Reading mail settings from properties: " + config);

    logger.debug("Creating reports zip file");
    File reportFile = zip(config);
    if (reportFile == null) {
      String jobId = config.getProperty(Constants.PROPERTY_REPORT_JOB_ID);
      logger.info("No reports with data were created for job id: " + jobId + ". No email will be sent");
      return;
    }
    logger.debug("Created reports zip file: " + reportFile.getAbsolutePath());

    String host = config.getProperty(Constants.PROPERTY_MAIL_SMTP_SERVER);
    String port = config.getProperty(Constants.PROPERTY_MAIL_SMTP_PORT);
    String sslPort = config.getProperty(Constants.PROPERTY_MAIL_SSL_PORT);
    String auth = config.getProperty(Constants.PROPERTY_MAIL_SMTP_AUTH, "true");
    String startTlsEnable = config.getProperty(Constants.PROPERTY_MAIL_START_TLS_ENABLE, "true");
    String username = config.getProperty(Constants.PROPERTY_MAIL_SERVER_USERNAME);
    String password = config.getProperty(Constants.PROPERTY_MAIL_SERVER_PASSWORD);
    String mailFrom = config.getProperty(Constants.PROPERTY_MAIL_FROM_ADDRESS);

    RequestReportsForm reportForm = (RequestReportsForm) config.get(Constants.PROPERTY_REPORT_FORM);

    String mailTo = null;
    if (reportForm != null) {
      mailTo = reportForm.getEmailTo();
    } else {
      mailTo = config.getProperty(Constants.PROPERTY_MAIL_TO_ADDRESSES);
    }

    if (mailTo == null) {
      throw new IllegalArgumentException(
          "Mail To address must be specficied through either the Web form or in the configuration properties with key: "
              + Constants.PROPERTY_MAIL_TO_ADDRESSES);

    }

    String mailSubject = config.getProperty(Constants.PROPERTY_MAIL_SUBJECT);
    String mailText = config.getProperty(Constants.PROPERTY_MAIL_TEXT);

    Properties prop = new Properties();

    prop.put("mail.smtp.host", host);
    prop.put("mail.smtp.port", port);
    prop.put("mail.smtp.auth", auth);
    prop.put("mail.smtp.starttls.enable", startTlsEnable); // TLS

    if (sslPort != null && !sslPort.trim().equals("")) {
      prop.put("mail.smtp.socketFactory.port", sslPort);
      prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(mailFrom));

      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
      message.setSubject(mailSubject);
      message.setText(mailText);

      MimeBodyPart messageBodyPart = new MimeBodyPart();
      MimeMultipart multipart = new MimeMultipart();
      DataSource source = new FileDataSource(reportFile);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(reportFile.getName());
      multipart.addBodyPart(messageBodyPart);

      message.setContent(multipart);

      Transport.send(message);

      logger.debug("Mail Sent");

    } catch (MessagingException e) {
      e.printStackTrace();
    }

  }

  private File zip(Properties config) {

    try {
      String jobId = config.getProperty(Constants.PROPERTY_REPORT_JOB_ID);
      ReportGenerationResult reportGenerationResult = (ReportGenerationResult) config
          .get(Constants.OBJECT_KEY_REPORT_GENERATION_RESULT);
      List<File> reportsWithData = (List<File>) reportGenerationResult.getGeneratedReportsWithData();

      if (reportsWithData.size() == 0) {
        logger.info("No reports with data for job: " + jobId + ". Mail will not be sent.");
        return null;
      }
      String reportsBaseDirectory = config.getProperty(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH);

      File reportsDirectory = new File(reportsBaseDirectory + System.getProperty("file.separator") + jobId);

      File reportsZip = new File(
          reportsDirectory.getAbsolutePath() + System.getProperty("file.separator") + jobId + "_AlertReports.zip");
      final FileOutputStream fos = new FileOutputStream(reportsZip);
      ZipOutputStream zipOut = new ZipOutputStream(fos);

      for (File reportFile : reportsDirectory.listFiles()) {
        if (!reportFile.getName().endsWith(".csv")) {
          continue;
        }

        boolean reportHasData = false;
        for (File reportWithData : reportsWithData) {
          if (reportWithData.getAbsolutePath().equals(reportFile.getAbsolutePath())) {
            reportHasData = true;
            break;
          }
        }
        if (!reportHasData) {
          continue;
        }

        FileInputStream fis = new FileInputStream(reportFile);
        ZipEntry zipEntry = new ZipEntry(reportFile.getName());
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
          zipOut.write(bytes, 0, length);
        }
        fis.close();
      }

      zipOut.close();
      fos.close();
      return reportsZip;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
