package com.visibleai.sebi.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.builder.ReportBuilder;
import com.visibleai.sebi.report.printer.ReportPrinter;
import com.visibleai.sebi.report.printer.SebiAlertsReportPrinter;

public class Orchestrator {

    private List<ReportBuilder> reportBuilders;

    private ReportPrinter reportPrinter;

    private Properties properties;

    public Orchestrator(Properties properties) throws FileNotFoundException {

        reportBuilders = new ReportBuilderFactory().createReportBuilders(properties);

        reportPrinter = new SebiAlertsReportPrinter();
        this.properties = properties;

    }

    public void generateReports() throws IOException {

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

        // For each CSV record do the validations
        for (int i = 0; i < csvRecords.size(); i++) {
            CSVRecord csvRecord = csvRecords.get(i);

            VisitorEntry visitorEntry = new VisitorEntry(csvRecord);

            for (ReportBuilder reportBuilder : reportBuilders) {
                reportBuilder.build(visitorEntry);
            }

        }

        for (ReportBuilder reportBuilder : reportBuilders) {

            Report report = reportBuilder.getReport();
            String reportOutputFilePath = properties.getProperty(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH);
            File reportFile = new File(reportOutputFilePath + System.getProperty("file.separator") + report.getFileName());
            PrintStream printStream = new PrintStream(reportFile);
            reportPrinter.print(report, printStream);
            printStream.flush();
            printStream.close();
        }

    }

}
