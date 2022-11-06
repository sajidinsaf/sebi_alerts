package com.visibleai.sebi.report;

import java.util.Date;

public interface Report {

    public String getFileName();

    public String getTitle();

    public Date getDate();

    public ReportData getReportData();
}
