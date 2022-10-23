package com.visibleai.sebi.report.builder;

import org.apache.commons.csv.CSVRecord;

public interface ReportBuilder {

  public void build(CSVRecord csvRecord);

}
