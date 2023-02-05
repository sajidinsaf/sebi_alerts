package com.visibleai.sebi;

import java.io.FileReader;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.visibleai.sebi.model.Constants;

public class MailSenderMain {

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

    System.out.println("Reading email properties from: " + configFilePath);

    properties.load(fileReader);

    sendMail(properties);
  }

  private static void sendMail(Properties config) {

    String host = config.getProperty(Constants.PROPERTY_MAIL_SMTP_SERVER);
    String port = config.getProperty(Constants.PROPERTY_MAIL_SMTP_PORT);
    String sslPort = config.getProperty(Constants.PROPERTY_MAIL_SSL_PORT);
    String auth = config.getProperty(Constants.PROPERTY_MAIL_SMTP_AUTH, "true");
    String startTlsEnable = config.getProperty(Constants.PROPERTY_MAIL_START_TLS_ENABLE, "true");
    String username = config.getProperty(Constants.PROPERTY_MAIL_SERVER_USERNAME);
    String password = config.getProperty(Constants.PROPERTY_MAIL_SERVER_PASSWORD);
    String mailFrom = config.getProperty(Constants.PROPERTY_MAIL_FROM_ADDRESS);
    String mailTo = config.getProperty(Constants.PROPERTY_MAIL_TO_ADDRESSES);

    String mailSubject = config.getProperty(Constants.PROPERTY_MAIL_SUBJECT);
    String mailText = config.getProperty(Constants.PROPERTY_MAIL_TEXT);

    Properties prop = new Properties();
    prop.put("mail.smtp.host", host);
    prop.put("mail.smtp.port", port);
    prop.put("mail.smtp.auth", auth);
    prop.put("mail.smtp.starttls.enable", startTlsEnable); // TLS

    if (sslPort != null) {
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

      Transport.send(message);

      System.out.println("Done");

    } catch (MessagingException e) {
      e.printStackTrace();
    }

  }

}
