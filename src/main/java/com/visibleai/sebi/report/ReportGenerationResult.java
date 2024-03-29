package com.visibleai.sebi.report;

import java.io.File;
import java.util.List;

public class ReportGenerationResult {

  private List<File> generatedReports;
  private List<File> generatedReportsWithData;
  private List<File> failedReports;

  public ReportGenerationResult() {
    // TODO Auto-generated constructor stub
  }

  public ReportGenerationResult(List<File> generatedReports, List<File> failedReports,
      List<File> generatedReportsWithData) {
    super();
    this.generatedReports = generatedReports;
    this.failedReports = failedReports;
    this.generatedReportsWithData = generatedReportsWithData;
  }

  public List<File> getGeneratedReports() {
    return generatedReports;
  }

  public void setGeneratedReports(List<File> generatedReports) {
    this.generatedReports = generatedReports;
  }

  public List<File> getFailedReports() {
    return failedReports;
  }

  public void setFailedReports(List<File> failedReports) {
    this.failedReports = failedReports;
  }

  public List<File> getGeneratedReportsWithData() {
    return generatedReportsWithData;
  }

  public void setGeneratedReportsWithData(List<File> generatedReportsWithData) {
    this.generatedReportsWithData = generatedReportsWithData;
  }

}
