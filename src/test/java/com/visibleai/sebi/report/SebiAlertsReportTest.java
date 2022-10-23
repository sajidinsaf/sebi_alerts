package com.visibleai.sebi.report;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class SebiAlertsReportTest {

    @Test
    public void testAddTitle() {
        SebiAlertsReport sebiAlertsReport = new SebiAlertsReport();

        String title = "Media Visitor Report";
        sebiAlertsReport.setTitle(title);
        assertEquals(title, sebiAlertsReport.getTitle());

        Date dateOfReport = new Date();
        sebiAlertsReport.setDate(dateOfReport);
        assertEquals(dateOfReport, sebiAlertsReport.getDate());

        ReportData dataInReport = new TableReportData();
        sebiAlertsReport.setReportData(dataInReport);
        assertEquals(dataInReport, sebiAlertsReport.getReportData());

    }

}
