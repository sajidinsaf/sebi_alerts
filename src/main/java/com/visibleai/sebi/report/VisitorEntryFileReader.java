package com.visibleai.sebi.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;

public class VisitorEntryFileReader {

    public VisitorEntryFileReader() {

    }

    public List<VisitorEntry> getVisitorEntries(Properties properties) throws IOException {

        String fileName = null;

        fileName = properties.getProperty(Constants.PROPERTY_VISITOR_ENTRY_FILE);

        // create an instance of the file
        File file = new File(fileName);

        // create a reader for this file
        BufferedReader reader = new BufferedReader(new FileReader(file));

        // Parse the file using the apache CSV library
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

        // Get the list of CSV records
        List<CSVRecord> csvRecords = parser.getRecords();

        List<VisitorEntry> visitorEntries = new ArrayList<VisitorEntry>();

        // For each CSV record do the validations
        for (int i = 0; i < csvRecords.size(); i++) {
            CSVRecord csvRecord = csvRecords.get(i);

            VisitorEntry visitorEntry = new VisitorEntry(csvRecord);
            visitorEntries.add(visitorEntry);
        }
        return visitorEntries;
    }

}
