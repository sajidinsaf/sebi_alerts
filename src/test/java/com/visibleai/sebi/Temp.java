package com.visibleai.sebi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;

public class Temp {

    public Temp() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) throws Exception {

        // create an instance of the file
        File file = new File(
                "/Users/aarishois/Applications/Development/Projects/sebi_alerts/src/main/resources/sample-visitor-record-1000.csv");

        File outputFile = new File(
                "/Users/aarishois/Applications/Development/Projects/temp/sample-visitor-record-1000_1.csv");

        // create a reader for this file
        BufferedReader reader = new BufferedReader(new FileReader(file));

        // Parse the file using the apache CSV library
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

        PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
        List<CSVRecord> csvRecords = parser.getRecords();

        pw.println(
                "ID Proof,Location,Division,Department,Visitor Name,Visitor Mobile,Access Card,Visitor Company,To Meet,Remarks,Time In,Time Out,Visit Duration,Logged Out,Host Log,Pass ID,Auth By,Questions,Type of Visitor,Assets");

        for (int i = 0; i < csvRecords.size(); i++) {
            CSVRecord csvRecord = csvRecords.get(i);

            VisitorEntry visitorEntry = new VisitorEntry(csvRecord);

            String line = toCsv(visitorEntry);
            pw.println(line);
        }

        pw.flush();
        pw.close();

    }

    public static String toCsv(VisitorEntry v) throws ParseException {

        Random rd = new Random();
        int visitDuration = rd.nextInt(5) + 1;

        return v.getIdProof() + "," + v.getLocation() + "," + v.getDivision() + "," + v.getDepartment() + ","
                + v.getVisitorName() + "," + v.getVisitorNumber() + "," + v.getAccessCardId() + ","
                + v.getVisitorCompany() + "," + v.getToMeet() + "," + v.getRemarks() + ","
                + formatTimeIn(v.getTimeIn(), Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT) + ","

                + formatTimeOut(v.getTimeOut(), "yyyy-MM-dd HH:mm:ss", v.getTimeIn(),
                        Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT, visitDuration)

                + "," + visitDuration + "," + v.getLoggedOut() + "," + v.getHostL() + "," + v.getPassId() + ","
                + v.getAuthBy() + ",\"" + v.getQuestions() + "\"," + v.getTypeOfVistor() + "," + v.getAssets();
    }

    public static Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    private static String formatTimeOut(String timeOut, String formatTimeOut, String timeIn, String formatTimeIn,
            int visitDuration) throws ParseException {

        Date dateTimeIn = new SimpleDateFormat(formatTimeIn).parse(timeIn);

        Date dateTimeInPlus1Hour = addHoursToJavaUtilDate(dateTimeIn, visitDuration);
        String mySqlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTimeInPlus1Hour);

        return mySqlDate;

    }

    private static String formatTimeIn(String time, String format) throws ParseException {

        if (time == null || time.trim().equals("")) {
            return "";
        }

        Date date = new SimpleDateFormat(format).parse(time);
        String mySqlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return mySqlDate;
    }

}
