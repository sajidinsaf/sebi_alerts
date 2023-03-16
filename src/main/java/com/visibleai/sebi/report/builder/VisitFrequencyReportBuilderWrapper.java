package com.visibleai.sebi.report.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;

public class VisitFrequencyReportBuilderWrapper implements ReportBuilder {

  private Logger logger = LoggerFactory.getLogger(VisitFrequencyReportBuilderWrapper.class);

  private List<VisitFrequencyReportBuilder> visitorFrequencyReportBuilders;
  private Map<String, List<FrequentVisitorDetail>> visitFrequencyCheckMap;
  private File tempDbFile = null;
  private DB db = null;

  public VisitFrequencyReportBuilderWrapper(List<VisitFrequencyReportBuilder> visitorFrequencyReportBuilders,
      Properties config) {
    this.visitorFrequencyReportBuilders = visitorFrequencyReportBuilders;
    tempDbFile = new File(
        config.getProperty(Constants.PROPERTY_REPORT_JOB_DIR) + System.getProperty("file.separator") + "visitorMapDB");

    db = DBMaker.fileDB(tempDbFile).make();
    logger.info("Created temp db file: " + tempDbFile.getAbsolutePath());
    visitFrequencyCheckMap = (Map<String, List<FrequentVisitorDetail>>) db.hashMap("VisitorFrequencyDetailMap")
        .createOrOpen();
    logger.debug("Initialised visit frequency check map");
    new HashMap<String, List<FrequentVisitorDetail>>();

    for (VisitFrequencyReportBuilder visitorFrequencyReportBuilder : visitorFrequencyReportBuilders) {
      visitorFrequencyReportBuilder.setVisitFrequencyCheckMap(visitFrequencyCheckMap);
    }
  }

  @Override
  public void build(VisitorEntry visitorEntry) {

    FrequentVisitorDetail frequentVisitorDetail = new FrequentVisitorDetail(visitorEntry);

    String phoneNumber = frequentVisitorDetail.getVisitorNumber();
    if (phoneNumber == null) {
      logger.warn("Cannot process entry: " + visitorEntry + " because the key field PhoneNumber is null");
      return;
    }
    // logger.debug("Adding frequency detail for phone number: " + phoneNumber);
    List<FrequentVisitorDetail> frequentVisitorDetailList = visitFrequencyCheckMap.getOrDefault(phoneNumber,
        new ArrayList<FrequentVisitorDetail>());
    frequentVisitorDetailList.add(frequentVisitorDetail);
    if (frequentVisitorDetailList.size() > 20) {
      logger.debug("Total current frequency details for phone number: " + phoneNumber + " are ["
          + frequentVisitorDetailList.size() + "]");
    }

    visitFrequencyCheckMap.put(phoneNumber, frequentVisitorDetailList);

    db.commit();

  }

  @Override
  public List<Report> getReports() {
    List<Report> reports = new ArrayList<>();
    for (VisitFrequencyReportBuilder visitorFrequencyReportBuilder : visitorFrequencyReportBuilders) {

      reports.addAll(visitorFrequencyReportBuilder.getReports());
    }
    db.close();
    tempDbFile.delete();
    return reports;

  }

  @Override
  public String getName() {
    return "VisitFrequencyReportBuilderWrapper";
  }

}
