package com.visibleai.sebi.report;

import java.util.List;

public interface ReportData {
    public List<String> getHeader();

    public List<List<String>> getRows();

    public void addRow(List<String> row);
}
