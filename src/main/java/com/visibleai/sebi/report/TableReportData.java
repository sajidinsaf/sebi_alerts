package com.visibleai.sebi.report;

import java.util.ArrayList;
import java.util.List;

public class TableReportData implements ReportData {
    private List<List<String>> rows = new ArrayList<List<String>>();

    private List<String> header = null;

    public List<String> getHeader() {

        return header;
    }

    public void setHeader(List<String> headerNames) {
        header = headerNames;
    }

    public void addRow(List<String> row) {
        rows.add(row);
    }

    public List<List<String>> getRows() {
        return rows;
    }

}
