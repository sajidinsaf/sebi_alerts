package com.visibleai.sebi;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.visibleai.sebi.db.VisitorEntryDatabaseReader;
import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Orchestrator;
import com.visibleai.sebi.report.VisitorEntryFileReader;
import com.visibleai.sebi.util.DateUtil;

public class Main {

  public static void main(String[] args) throws Exception {

    System.out.println("Alerts report system starting");

    Properties properties = new Properties();
    String configFilePath = Constants.DEFAULT_CONFIG_FILE_PATH;

    if (args != null && args.length > 0) {
      configFilePath = args[0];
    }

    FileReader fileReader = new FileReader(configFilePath);

    properties.load(fileReader);

    System.out.println("Loaded configuration properties: " + properties);

    System.out.println("Running report generation from base directory: " + new File(".").getAbsolutePath());

    Orchestrator orchestrator = new Orchestrator(properties, new DateUtil());

    String visitorEntrySource = properties.getProperty(Constants.VISITOR_ENTRY_SOURCE,
        Constants.DEFAULT_VISITOR_ENTRY_SOURCE);

    List<VisitorEntry> visitorEntries = new ArrayList<VisitorEntry>();

    if (visitorEntrySource.equals(Constants.VISITOR_ENTRY_SOURCE_DB)) {
      visitorEntries = new VisitorEntryDatabaseReader().getVisitorEntries(properties);
    } else {
      visitorEntries = new VisitorEntryFileReader().getVisitorEntries(properties);
    }

    orchestrator.generateReports(visitorEntries);

  }

}
