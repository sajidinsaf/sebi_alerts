package com.visibleai.sebi.model;

public abstract class Constants {

  public static final String VISITOR_ENTRY_SOURCE = "visitor.entry.source";
  public static final String VISITOR_ENTRY_SOURCE_DB = "db";
  public static final String VISITOR_ENTRY_SOURCE_FILE = "file";

  public static final String DEFAULT_VISITOR_ENTRY_SOURCE = VISITOR_ENTRY_SOURCE_FILE;

  public static final String DEFAULT_VISITOR_ENTRY_DATE_FORMAT = "dd MMM yyyy hh:mma";
  public static final String JOB_ID_DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";

  public static final String BROKERS_LIST_FILE = Constants.class.getClassLoader().getResource("BrokerList.txt")
      .getFile();
  public static final String GOVT_ORGS_FILE = Constants.class.getClassLoader().getResource("GovtOrgList.txt").getFile();
  public static final String EMPLOYEE_WATCH_LIST_FILE = Constants.class.getClassLoader()
      .getResource("EmployeeWatchList.txt").getFile();
  public static final String DEFAULT_CONFIG_FILE_PATH = Constants.class.getClassLoader()
      .getResource("config.properties").getFile();

  public static final String REPORT_DATE_FORMAT = "dd MMM yyyy";

  public static final String PROPERTY_VISITOR_ENTRY_FILE = "visitor.entry.file";
  public static final String PROPERTY_BROKER_LIST_FILE = "broker.list.file";
  public static final String PROPERTY_GOVT_ORG_LIST_FILE = "govt.org.list.file";
  public static final String PROPERTY_EMPLOYEE_MATCH_LIST_FILE = "employee.match.list.file";
  public static final String PROPERTY_ENTRY_DATETIME_FORMAT = "entry.datetime.format";
  public static final String PROPERTY_REPORT_OUTPUT_FILE_PATH = "report.out.file.path";
  public static final String PROPERTY_REPORT_JOB_ID = "report.job.id";
  public static final String PROPERTY_REPORT_JOB_DIR = "report.job.dir";
  public static final String PROPERTY_VISITOR_MATCH_LIST_FILE = "visitor.list.file";
  public static final String PROPERTY_LAST_DAY_OF_MONTH = "last.day.of.month";
  public static final String PROPERTY_VAMS_DB_URL = "vams.db.url";
  public static final String PROPERTY_VAMS_DB_USER = "vams.db.user";
  public static final String PROPERTY_VAMS_DB_PASSWORD = "vams.db.password";
  public static final String PROPERTY_VAMS_DB_QUERY = "vams.db.query";
  public static final String PROPERTY_VAMS_DB_TIME_OFFSET_IN_HOURS = "vams.db.time.offset.in.hours";
  public static final String PROPERTY_JDBC_DRIVER_CLASS = "jdbc.driver.class";
  public static final String PROPERTY_REPORT_FORM = "reports.form";

  public static final String PROPERTY_MAIL_SMTP_SERVER = "mail.smtp.server";
  public static final String PROPERTY_MAIL_SMTP_PORT = "mail.smtp.port";
  public static final String PROPERTY_MAIL_SSL_PORT = "mail.ssl.port";
  public static final String PROPERTY_MAIL_SMTP_AUTH = "mail.smtp.auth";
  public static final String PROPERTY_MAIL_START_TLS_ENABLE = "mail.start.tls.enable";
  public static final String PROPERTY_MAIL_SERVER_USERNAME = "mail.username";
  public static final String PROPERTY_MAIL_SERVER_PASSWORD = "mail.password";
  public static final String PROPERTY_MAIL_FROM_ADDRESS = "mail.from.address";
  public static final String PROPERTY_MAIL_TO_ADDRESSES = "mail.to.addresses";
  public static final String PROPERTY_MAIL_SUBJECT = "mail.subject";
  public static final String PROPERTY_MAIL_TEXT = "mail.text";

  public static final String PROPERTY_SERVER_TYPE = "server.type";
  public static final String PROPERTY_SERVER_TYPE_WEB = "web";
  public static final String PROPERTY_SERVER_TYPE_LOCAL = "standalone";

  public static final long ONE_MONTH_IN_MILLISECONDS = 2592000000l;

}
