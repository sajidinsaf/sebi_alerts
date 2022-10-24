package com.visibleai.sebi.report.builder;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.ReportData;

public class MediaVisitorReportBuilderTest {

    @Test
    public void testMediaVisitorReportBuilder() {
        // diesel car
        MediaVisitorReportBuilder mediaVisitorReportBuilder = new MediaVisitorReportBuilder();

        // petrol
        VisitorEntry nonMediaVisitorEntry = new VisitorEntry();
        nonMediaVisitorEntry.setAccessCardId("V-123");
        nonMediaVisitorEntry.setVisitorNumber("1234567890");
        nonMediaVisitorEntry.setVisitorName("Shahrukh Khan");
        nonMediaVisitorEntry.setToMeet("Rahul Mishra");
        nonMediaVisitorEntry.setTimeIn("21 Jul 2022 10:18am");

        mediaVisitorReportBuilder.build(nonMediaVisitorEntry);

        // diesel
        VisitorEntry mediaVisitorEntry = new VisitorEntry();
        mediaVisitorEntry.setAccessCardId("M-123");
        mediaVisitorEntry.setVisitorNumber("1238392890");
        mediaVisitorEntry.setVisitorName("Salman Khan");
        mediaVisitorEntry.setToMeet("Amir Khan");
        mediaVisitorEntry.setTimeIn("22 Jun 2022 11:32am");

        mediaVisitorReportBuilder.build(mediaVisitorEntry);

        Report mediaVisitorReport = mediaVisitorReportBuilder.getReport();
        assertEquals("Media Visitor Report", mediaVisitorReport.getTitle());

        ReportData mediaVisitorReportData = mediaVisitorReport.getReportData();
        List<String> header = mediaVisitorReportData.getHeader();
        assertEquals("Name", header.get(0));
        assertEquals("Phone Number", header.get(1));
        assertEquals("Meeting", header.get(2));
        assertEquals("Date", header.get(3));
        assertEquals("Access Card ID", header.get(4));
        assertEquals("Comments", header.get(5));

        List<List<String>> rows = mediaVisitorReportData.getRows();
        assertEquals(1, rows.size());

        List<String> row = rows.get(0);
        assertEquals("Salman Khan", row.get(0));
        assertEquals("1238392890", row.get(1));
        assertEquals("Amir Khan", row.get(2));
        assertEquals("22 Jun 2022 11:32am", row.get(3));
        assertEquals("M-123", row.get(4));
        assertEquals("", row.get(5));

        Date date = mediaVisitorReport.getDate();
        Date today = new Date();

        Calendar reportCalendar = Calendar.getInstance();
        Calendar todayCalendar = Calendar.getInstance();

        reportCalendar.setTime(date);
        todayCalendar.setTime(today);

        int reportDayOfTheMonth = reportCalendar.get(Calendar.DAY_OF_MONTH);
        int todayDayOfTheMonth = todayCalendar.get(Calendar.DAY_OF_MONTH);
        assertEquals(todayDayOfTheMonth, reportDayOfTheMonth);

        int reportMonthOfTheYear = reportCalendar.get(Calendar.MONTH);
        int todayMonthOfTheYear = todayCalendar.get(Calendar.MONTH);
        assertEquals(todayMonthOfTheYear, reportMonthOfTheYear);

        int reportYear = reportCalendar.get(Calendar.YEAR);
        int todayYear = todayCalendar.get(Calendar.YEAR);
        assertEquals(todayYear, reportYear);
    }

}
