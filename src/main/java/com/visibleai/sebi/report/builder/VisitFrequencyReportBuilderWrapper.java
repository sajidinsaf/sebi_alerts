package com.visibleai.sebi.report.builder;

import java.util.ArrayList;
import java.util.List;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;

public class VisitFrequencyReportBuilderWrapper implements ReportBuilder {

  private List<VisitFrequencyReportBuilder> visitorFrequencyReportBuilders;

  public VisitFrequencyReportBuilderWrapper(List<VisitFrequencyReportBuilder> visitorFrequencyReportBuilders) {
    this.visitorFrequencyReportBuilders = visitorFrequencyReportBuilders;
  }

  @Override
  public void build(VisitorEntry visitorEntry) {
    FrequentVisitorDetail frequentVisitorDetail = new FrequentVisitorDetail(visitorEntry);
    for (VisitFrequencyReportBuilder visitorFrequencyReportBuilder : visitorFrequencyReportBuilders) {
      visitorFrequencyReportBuilder.build(frequentVisitorDetail);
    }

  }

  @Override
  public List<Report> getReports() {
    List<Report> reports = new ArrayList<>();
    for (VisitFrequencyReportBuilder visitorFrequencyReportBuilder : visitorFrequencyReportBuilders) {
      reports.addAll(visitorFrequencyReportBuilder.getReports());
    }
    return reports;
  }

  @Override
  public String getName() {
    return "VisitFrequencyReportBuilderWrapper";
  }

}
