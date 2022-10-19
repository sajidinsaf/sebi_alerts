package com.visibleai.sebi.report.builder;

import org.apache.commons.csv.CSVRecord;

public interface Builder {

  public void builder(CSVRecord csvRecord);

}
