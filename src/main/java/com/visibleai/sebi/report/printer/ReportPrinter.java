package com.visibleai.sebi.report.printer;

import java.io.PrintStream;

import com.visibleai.sebi.report.Report;

public interface ReportPrinter {
    public void print(Report report, PrintStream outputStream);
}
