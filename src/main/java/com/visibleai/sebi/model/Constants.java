package com.visibleai.sebi.model;

public abstract class Constants {

    public static final String DEFAULT_VISITOR_ENTRY_DATE_FORMAT = "dd MMM yyyy hh:mma";
    public static final String BROKERS_LIST_FILE = Constants.class.getClassLoader().getResource("BrokerList.txt").getFile();
    public static final String GOVT_ORGS_FILE = Constants.class.getClassLoader().getResource("GovtOrgList.txt")
            .getFile();
    public static final String EMPLOYEE_WATCH_LIST_FILE = Constants.class.getClassLoader()
            .getResource("EmployeeWatchList.txt").getFile();

}
