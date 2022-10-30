package com.visibleai.sebi.report.builder;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.ReportData;

public class VisitFrequencyReportBuilderTest {

    @Test
    public void testVisitFrequencyReportBuilder() {
        int numberOfDays = 7;
        int frequencyViolationNumber = 3;
        VisitFrequencyReportBuilder visitFrequencyReportBuilder = new VisitFrequencyReportBuilder(numberOfDays,
                frequencyViolationNumber);

        Date todaysDate = new Date();
        Calendar numberOfDaysAgo = Calendar.getInstance();
        numberOfDaysAgo.setTime(todaysDate);
        numberOfDaysAgo.add(Calendar.DATE, -numberOfDays + 1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);

        String dateOfViolationVisitor1 = simpleDateFormat.format(numberOfDaysAgo.getTime());
        numberOfDaysAgo.setTime(todaysDate);
        numberOfDaysAgo.add(Calendar.DATE, -numberOfDays + 1);
        VisitorEntry violationVisitor = new VisitorEntry();
        violationVisitor.setVisitorNumber("1234567890");
        violationVisitor.setVisitorName("Chris Tucker");
        violationVisitor.setToMeet("Jackie Chan");
        violationVisitor.setTimeIn(dateOfViolationVisitor1);
        violationVisitor.setVisitorCompany("ICICI Bank");
        violationVisitor.setAccessCardId("A-123");

        visitFrequencyReportBuilder.build(violationVisitor);

        String dateOfNonViolationVisitor = simpleDateFormat.format(numberOfDaysAgo.getTime());
        numberOfDaysAgo.setTime(todaysDate);
        numberOfDaysAgo.add(Calendar.DATE, -numberOfDays + 2);

        VisitorEntry nonViolationVisitor = new VisitorEntry();
        nonViolationVisitor.setVisitorNumber("4573796467");
        nonViolationVisitor.setVisitorName("Dwayne Johnson");
        nonViolationVisitor.setToMeet("Kevin Hart");
        nonViolationVisitor.setTimeIn(dateOfNonViolationVisitor);
        nonViolationVisitor.setVisitorCompany("TOSHIBA");
        nonViolationVisitor.setAccessCardId("V-892");

        visitFrequencyReportBuilder.build(nonViolationVisitor);

        String dateOfViolationVisitor2 = simpleDateFormat.format(numberOfDaysAgo.getTime());
        numberOfDaysAgo.setTime(todaysDate);
        numberOfDaysAgo.add(Calendar.DATE, -numberOfDays + 2);
        VisitorEntry violationVisitorSecondTime = new VisitorEntry();
        violationVisitorSecondTime.setVisitorNumber("1234567890");
        violationVisitorSecondTime.setVisitorName("Will Smith");
        violationVisitorSecondTime.setToMeet("Jackie Chan");
        violationVisitorSecondTime.setTimeIn(dateOfViolationVisitor2);
        violationVisitorSecondTime.setVisitorCompany("ICICI Bank");
        violationVisitorSecondTime.setAccessCardId("A-123");

        visitFrequencyReportBuilder.build(violationVisitorSecondTime);

        String dateOfNonViolationVisitor2 = simpleDateFormat.format(numberOfDaysAgo.getTime());
        numberOfDaysAgo.setTime(todaysDate);
        numberOfDaysAgo.add(Calendar.DATE, -numberOfDays + 4);

        VisitorEntry nonViolationVisitor2 = new VisitorEntry();
        nonViolationVisitor2.setVisitorNumber("4573796467");
        nonViolationVisitor2.setVisitorName("Dwayne Johnson");
        nonViolationVisitor2.setToMeet("Kevin Hart");
        nonViolationVisitor2.setTimeIn(dateOfNonViolationVisitor2);
        nonViolationVisitor2.setVisitorCompany("TOSHIBA");
        nonViolationVisitor2.setAccessCardId("V-892");

        visitFrequencyReportBuilder.build(nonViolationVisitor2);

        String dateOfViolationVisitor3 = simpleDateFormat.format(numberOfDaysAgo.getTime());
        numberOfDaysAgo.setTime(todaysDate);
        numberOfDaysAgo.add(Calendar.DATE, -numberOfDays + 4);
        VisitorEntry violationVisitorThirdTime = new VisitorEntry();
        violationVisitorThirdTime.setVisitorNumber("1234567890");
        violationVisitorThirdTime.setVisitorName("Chris Tucker");
        violationVisitorThirdTime.setToMeet("Jackie Chan");
        violationVisitorThirdTime.setTimeIn(dateOfViolationVisitor3);
        violationVisitorThirdTime.setVisitorCompany("ICICI Bank");
        violationVisitorThirdTime.setAccessCardId("A-123");

        visitFrequencyReportBuilder.build(violationVisitorThirdTime);

        Report betweenSevenDaysReport = visitFrequencyReportBuilder.getReport();
        assertEquals(numberOfDays + " Day Check Report", betweenSevenDaysReport.getTitle());

        ReportData betweenSevenDaysReportData = betweenSevenDaysReport.getReportData();
        List<String> header = betweenSevenDaysReportData.getHeader();
        assertEquals("Phone Number", header.get(0));
        assertEquals("Name", header.get(1));
        assertEquals("Meeting with", header.get(2));
        assertEquals("Date", header.get(3));
        assertEquals("Visitors Company", header.get(4));
        assertEquals("Type Of Visitor", header.get(5));
        assertEquals("Comments", header.get(6));

        List<List<String>> rows = betweenSevenDaysReportData.getRows();
        assertEquals(1, rows.size());

        List<String> row = rows.get(0);
        assertEquals("1234567890", row.get(0));
        assertEquals("Chris Tucker|Will Smith|Chris Tucker|", row.get(1));
        assertEquals("Jackie Chan|Jackie Chan|Jackie Chan|", row.get(2));
        assertEquals(dateOfViolationVisitor1 + "|" + dateOfViolationVisitor2 + "|" + dateOfViolationVisitor3 + "|",
                row.get(3));
        assertEquals("ICICI Bank|ICICI Bank|ICICI Bank|", row.get(4));
        assertEquals("A-123|A-123|A-123|", row.get(5));
        assertEquals("", row.get(6));
    }

}
