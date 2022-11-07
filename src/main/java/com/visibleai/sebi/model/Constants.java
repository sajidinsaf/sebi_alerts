package com.visibleai.sebi.model;

public abstract class Constants {

  public static final String DEFAULT_VISITOR_ENTRY_DATE_FORMAT = "dd MMM yyyy hh:mma";
  public static final String BROKERS_LIST_FILE = Constants.class.getClassLoader().getResource("BrokerList.txt")
      .getFile();
  public static final String GOVT_ORGS_FILE = Constants.class.getClassLoader().getResource("GovtOrgList.txt").getFile();
  public static final String EMPLOYEE_WATCH_LIST_FILE = Constants.class.getClassLoader()
      .getResource("EmployeeWatchList.txt").getFile();
  public static final String DEFAULT_CONFIG_FILE_PATH = Constants.class.getClassLoader()
      .getResource("config.properties").getFile();

  public static final String PROPERTY_VISITOR_ENTRY_FILE = "visitor.entry.file";
  public static final String PROPERTY_BROKER_LIST_FILE = "broker.list.file";
  public static final String PROPERTY_GOVT_ORG_LIST_FILE = "govt.org.list.file";
  public static final String PROPERTY_EMPLOYEE_MATCH_LIST_FILE = "employee.match.list.file";
  public static final String PROPERTY_ENTRY_DATETIME_FORMAT = "entry.datetime.format";
  public static final String PROPERTY_REPORT_OUTPUT_FILE_PATH = "report.out.file.path";

  public static final String REPORT_DATE_FORMAT = "dd MMM yyyy";
}
