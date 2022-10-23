package com.visibleai.sebi.report;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TableReportDataTest {

    @Test
    public void testAddRow() {
        TableReportData tableReportData = new TableReportData();

        List<String> row1 = new ArrayList<String>();

        String name1 = "Bob";
        String meeting1 = "Tom";
        String phoneNumber1 = "+91 12345 67890";
        row1.add(name1);
        row1.add(meeting1);
        row1.add(phoneNumber1);
        tableReportData.addRow(row1);

        List<String> row2 = new ArrayList<String>();

        String name2 = "John";
        String meeting2 = "Rajesh";
        String phoneNumber2 = "+91 09876 54321";
        row2.add(name2);
        row2.add(meeting2);
        row2.add(phoneNumber2);
        tableReportData.addRow(row2);

        List<String> row3 = new ArrayList<String>();

        String name3 = "Timmy";
        String meeting3 = "Tony";
        String phoneNumber3 = "+91 77388 05754";
        row3.add(name3);
        row3.add(meeting3);
        row3.add(phoneNumber3);
        tableReportData.addRow(row3);

        List<List<String>> rows = tableReportData.getRows();
        assertEquals(true, rows.size() == 3);
        assertEquals(row1, rows.get(0));
        assertEquals(row2, rows.get(1));
        assertEquals(row3, rows.get(2));

        tableReportData.getRows();
    }

    @Test
    public void testSetHeader() {
        TableReportData tableReportData = new TableReportData();

        List<String> header = new ArrayList<String>();
        String header1 = "Name";
        String header2 = "Meeting";
        header.add(header1);
        tableReportData.setHeader(header);

        assertEquals(header, tableReportData.getHeader());

    }
}
