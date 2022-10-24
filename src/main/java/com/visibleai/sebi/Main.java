package com.visibleai.sebi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.validation.AccessCardValidator;
import com.visibleai.sebi.validation.VisitorTimeValidator;

public class Main {

    public static void main(String[] args) throws Exception {

        String fileName = null;

        // If the file name is sent in the args then use that file name
        if (args != null && args.length > 0) {
            fileName = args[0];
        } else {
            // otherwise use the file name from the resourcs package
            fileName = Main.class.getClassLoader().getResource("sample-visitor-record.csv").getFile();
        }

        // create an instance of the file
        File file = new File(fileName);

        // create a reader for this file
        BufferedReader reader = new BufferedReader(new FileReader(file));

        // Parse the file using the apache CSV library
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

        // Get the list of CSV records
        List<CSVRecord> csvRecords = parser.getRecords();

        // For each CSV record do the validations
        for (int i = 0; i < csvRecords.size(); i++) {
            CSVRecord csvRecord = csvRecords.get(i);

            VisitorEntry visitorEntry = new VisitorEntry(csvRecord);

            String accessCardId = csvRecord.get(6);
            String dayIn = csvRecord.get(10);

            AccessCardValidator accessCardValidator = new AccessCardValidator();
            VisitorTimeValidator visitorTimeValidator = new VisitorTimeValidator();

            // ternary operator
            String idCardMessage = accessCardValidator.isMedia(accessCardId) ? "This is a media visitor. Investigate"
                    : "This is a non-media visitor";

            String dayInMessage = visitorTimeValidator.isOutOfWorkingHours(dayIn)
                    ? "This is an out of hours visit. Investigate"
                    : "This is a regular visit.";

            System.out.println(accessCardId + " : " + idCardMessage);
            System.out.println(dayIn + " : " + dayInMessage);

        }

//    for (CSVRecord csvRecord : parser) {
//      System.out.println(csvRecord);
//    }

//    while ((line = reader.readLine()) != null) {
//      System.out.println(line);
//    }

        // reader.close();

    }

}
