package com.visibleai.sebi.report.builder;

import java.util.List;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;

public interface ReportBuilder {

  public void build(VisitorEntry visitorEntry);

  public List<Report> getReports();

  public String getName();

}
