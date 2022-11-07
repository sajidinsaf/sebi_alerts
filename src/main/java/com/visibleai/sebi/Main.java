package com.visibleai.sebi;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.Orchestrator;
import com.visibleai.sebi.util.DateUtil;

public class Main {

  public static void main(String[] args) throws Exception {
    Properties properties = new Properties();
    String configFilePath = Constants.DEFAULT_CONFIG_FILE_PATH;

    if (args != null && args.length > 0) {
      configFilePath = args[0];
    }

    FileReader fileReader = new FileReader(configFilePath);

    System.out.println("Running report generation from base directory: " + new File(".").getAbsolutePath());

    properties.load(fileReader);

    Orchestrator orchestrator = new Orchestrator(properties, new DateUtil());

    orchestrator.generateReports();

  }

}
