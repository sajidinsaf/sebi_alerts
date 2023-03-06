package com.visibleai.sebi.report.builder;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;

public interface ReportBuilder {

  public void build(VisitorEntry visitorEntry);

  public Report getReport();

  public String getName();

}
