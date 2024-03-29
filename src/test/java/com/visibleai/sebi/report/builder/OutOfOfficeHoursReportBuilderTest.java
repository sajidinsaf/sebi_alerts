package com.visibleai.sebi.report.builder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.ReportData;
import com.visibleai.sebi.util.DateUtil;

public class OutOfOfficeHoursReportBuilderTest {

  @Test
  public void testOutOfOfficeHoursReportBuilder() {
    Properties properties = new Properties();
    properties.setProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT, Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);

    OutOfOfficeHoursReportBuilder outOfOfficeHoursReportBuilder = new OutOfOfficeHoursReportBuilder(properties,
        new DateUtil());

    VisitorEntry duringOfficeHoursEntry = new VisitorEntry();
    duringOfficeHoursEntry.setVisitorName("Rajesh Potato");
    duringOfficeHoursEntry.setVisitorNumber("908763421");
    duringOfficeHoursEntry.setVisitorCompany("GODREJ");
    duringOfficeHoursEntry.setTimeIn("21 Jul 2022 10:18am");
    duringOfficeHoursEntry.setTimeOut("21 Jul 2022 12:18pm");
    duringOfficeHoursEntry.setToMeet("Croissant Breadwala");
    duringOfficeHoursEntry.setAccessCardId("V-234");

    outOfOfficeHoursReportBuilder.build(duringOfficeHoursEntry);

    VisitorEntry notDuringOfficeHoursEntry = new VisitorEntry();
    notDuringOfficeHoursEntry.setVisitorName("Aloo Batata");
    notDuringOfficeHoursEntry.setVisitorNumber("8274620197");
    notDuringOfficeHoursEntry.setVisitorCompany("ICICI Bank");
    notDuringOfficeHoursEntry.setTimeIn("21 Jul 2022 09:30pm");
    notDuringOfficeHoursEntry.setTimeOut("21 Jul 2022 03:30am");
    notDuringOfficeHoursEntry.setToMeet("Chintu Tomato");
    notDuringOfficeHoursEntry.setAccessCardId("A-682");

    outOfOfficeHoursReportBuilder.build(notDuringOfficeHoursEntry);

    Report outOfOfficeHoursReport = outOfOfficeHoursReportBuilder.getReports().get(0);
    assertEquals("Out Of Office Hours Report", outOfOfficeHoursReport.getTitle());

    ReportData outOfOfficeHoursReportData = outOfOfficeHoursReport.getReportData();
    List<String> header = outOfOfficeHoursReportData.getHeader();
    assertEquals("Name", header.get(0));
    assertEquals("Phone Number", header.get(1));
    assertEquals("Visitor Company", header.get(2));
    assertEquals("Date(Time In)", header.get(3));
    assertEquals("Date(Time Out)", header.get(4));
    assertEquals("Meeting", header.get(5));
    assertEquals("Type Of Visitor", header.get(6));
    assertEquals("Comments", header.get(7));

    List<List<String>> rows = outOfOfficeHoursReportData.getRows();
    assertEquals(1, rows.size());

    List<String> row = rows.get(0);
    assertEquals("Aloo Batata", row.get(0));
    assertEquals("8274620197", row.get(1));
    assertEquals("ICICI Bank", row.get(2));
    assertEquals("21 Jul 2022 09:30pm", row.get(3));
    assertEquals("21 Jul 2022 03:30am", row.get(4));
    assertEquals("Chintu Tomato", row.get(5));
    assertEquals("A-682", row.get(6));
    assertEquals("", row.get(7));
  }

  @Test
  public void testInvalidTimeFormatEntry() {
    Properties properties = new Properties();
    properties.setProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT, Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);

    OutOfOfficeHoursReportBuilder outOfOfficeHoursReportBuilder = new OutOfOfficeHoursReportBuilder(properties,
        new DateUtil());

    VisitorEntry invalidTimeFormatEntry = new VisitorEntry();
    invalidTimeFormatEntry.setVisitorName("Rajesh Potato");
    invalidTimeFormatEntry.setVisitorNumber("908763421");
    invalidTimeFormatEntry.setVisitorCompany("GODREJ");
    invalidTimeFormatEntry.setTimeIn("21 Jul 2022 22:30");
    invalidTimeFormatEntry.setTimeOut("21 Jul 2022 23:30");
    invalidTimeFormatEntry.setToMeet("Croissant Breadwala");
    invalidTimeFormatEntry.setAccessCardId("V-234");

    try {
      outOfOfficeHoursReportBuilder.build(invalidTimeFormatEntry);
      fail("Expected date parse exception not thrown");
    } catch (RuntimeException e) {
      assertThat(e.getMessage(), containsString(invalidTimeFormatEntry.getTimeIn()));
    }
    VisitorEntry notDuringOfficeHoursEntry = new VisitorEntry();
    notDuringOfficeHoursEntry.setVisitorName("Aloo Batata");
    notDuringOfficeHoursEntry.setVisitorNumber("8274620197");
    notDuringOfficeHoursEntry.setVisitorCompany("ICICI Bank");
    notDuringOfficeHoursEntry.setTimeIn("21 Jul 2022 09:30pm");
    notDuringOfficeHoursEntry.setTimeOut("21 Jul 2022 02:30am");
    notDuringOfficeHoursEntry.setToMeet("Chintu Tomato");
    notDuringOfficeHoursEntry.setAccessCardId("A-682");

    outOfOfficeHoursReportBuilder.build(notDuringOfficeHoursEntry);

    Report outOfOfficeHoursReport = outOfOfficeHoursReportBuilder.getReports().get(0);
    assertEquals("Out Of Office Hours Report", outOfOfficeHoursReport.getTitle());

    ReportData outOfOfficeHoursReportData = outOfOfficeHoursReport.getReportData();
    List<String> header = outOfOfficeHoursReportData.getHeader();
    assertEquals("Name", header.get(0));
    assertEquals("Phone Number", header.get(1));
    assertEquals("Visitor Company", header.get(2));
    assertEquals("Date(Time In)", header.get(3));
    assertEquals("Date(Time Out)", header.get(4));
    assertEquals("Meeting", header.get(5));
    assertEquals("Type Of Visitor", header.get(6));
    assertEquals("Comments", header.get(7));

    List<List<String>> rows = outOfOfficeHoursReportData.getRows();
    assertEquals(1, rows.size());

    List<String> row = rows.get(0);
    assertEquals("Aloo Batata", row.get(0));
    assertEquals("8274620197", row.get(1));
    assertEquals("ICICI Bank", row.get(2));
    assertEquals("21 Jul 2022 09:30pm", row.get(3));
    assertEquals("21 Jul 2022 02:30am", row.get(4));
    assertEquals("Chintu Tomato", row.get(5));
    assertEquals("A-682", row.get(6));
    assertEquals("", row.get(7));

  }
}
