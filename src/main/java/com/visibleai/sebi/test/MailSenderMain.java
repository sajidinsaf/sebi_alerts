package com.visibleai.sebi.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
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

        MailSenderMain mailSenderMain = new MailSenderMain();
        mailSenderMain.sendMail(properties);
    }

    public void sendMail(Properties config) {

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

            File reportFile = zip(config);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            MimeMultipart multipart = new MimeMultipart();
            DataSource source = new FileDataSource(reportFile);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(reportFile.getName());
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Mail Sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private File zip(Properties config) {

        try {
            File reportsDirectory = new File(config.getProperty(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH));
            File reportsZip = new File(reportsDirectory + System.getProperty("file.separator") + "AlertReports.zip");
            final FileOutputStream fos = new FileOutputStream(reportsZip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            for (File reportFile : reportsDirectory.listFiles()) {
                if (!reportFile.getName().endsWith(".csv")) {
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
