package com.visibleai.sebi.report.builder;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.ReportData;

public class ListCheckReportBuilderTest {

    @Test
    public void testListCheckReportBuilder() {
        List<String> checkList = Arrays.asList("ICICI Bank", "HDFC", "Bank Of India", "HDFC", "Axis Direct",
                "Paytm Money");
        String reportTitle = "List Check Report";
        ListCheckReportBuilder listCheckReportBuilder = new ListCheckReportBuilder(checkList, reportTitle);

        VisitorEntry isVisitorCompanyEntry = new VisitorEntry();
        isVisitorCompanyEntry.setVisitorCompany("ICICI Bank");
        isVisitorCompanyEntry.setVisitorName("Tom Cruise");
        isVisitorCompanyEntry.setVisitorNumber("1234567890");
        isVisitorCompanyEntry.setToMeet("Matt Damon");
        isVisitorCompanyEntry.setTimeIn("20 Oct 2022 10:30am");
        isVisitorCompanyEntry.setAccessCardId("V-174");
        listCheckReportBuilder.build(isVisitorCompanyEntry);

        VisitorEntry isNonVisitorCompanyEntry = new VisitorEntry();
        isNonVisitorCompanyEntry.setVisitorCompany("Godrej");
        isNonVisitorCompanyEntry.setVisitorName("George Clooney");
        isNonVisitorCompanyEntry.setVisitorNumber("3947503958");
        isNonVisitorCompanyEntry.setToMeet("Brad Pitt");
        isNonVisitorCompanyEntry.setTimeIn("23 Oct 2022 05:45am");
        isNonVisitorCompanyEntry.setAccessCardId("M-253");
        listCheckReportBuilder.build(isNonVisitorCompanyEntry);

        Report listCheckReport = listCheckReportBuilder.getReport();
        assertEquals(reportTitle, listCheckReport.getTitle());

        ReportData listCheckReportData = listCheckReport.getReportData();
        List<String> header = listCheckReportData.getHeader();
        assertEquals("Visitor Company", header.get(0));
        assertEquals("Name", header.get(1));
        assertEquals("Phone Number", header.get(2));
        assertEquals("Meeting", header.get(3));
        assertEquals("Date", header.get(4));
        assertEquals("Access Card ID", header.get(5));
        assertEquals("Comments", header.get(6));

        List<List<String>> rows = listCheckReportData.getRows();
        assertEquals(1, rows.size());

        List<String> row = rows.get(0);
        assertEquals("ICICI Bank", row.get(0));
        assertEquals("Tom Cruise", row.get(0));
        assertEquals("1234567890", row.get(1));
        assertEquals("Matt Damon", row.get(2));
        assertEquals("20 Oct 2022 10:30am", row.get(3));
        assertEquals("V-174", row.get(4));
        assertEquals("", row.get(5));

        Date date = listCheckReport.getDate();
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
