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

import com.visibleai.sebi.Main;
import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.builder.ReportBuilder;
import com.visibleai.sebi.report.printer.ReportPrinter;
import com.visibleai.sebi.report.printer.SebiAlertsReportPrinter;

public class Orchestrator {

    private List<ReportBuilder> reportBuilders;

    private ReportPrinter reportPrinter;

    private PrintStream printStream;

    public Orchestrator(Properties properties) throws FileNotFoundException {

        reportBuilders = new ReportBuilderFactory().createReportBuilders(properties);
        String reportOutputFilePath = properties.getProperty(Constants.PROPERTY_REPORT_OUTPUT_FILE_PATH);
        File file = new File(reportOutputFilePath);
        printStream = new PrintStream(file);
        reportPrinter = new SebiAlertsReportPrinter(printStream);

    }

    public void generateReports() throws IOException {

        String fileName = null;

        // If the file name is sent in the args then use that file name
//        if (args != null && args.length > 0) {
//            fileName = args[0];
//        } else {
        // otherwise use the file name from the resourcs package
        fileName = Main.class.getClassLoader().getResource("sample-visitor-record-1000.csv").getFile();
        // }

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
            printStream.println();
            printStream.println();
            reportPrinter.print(report);
        }

        printStream.flush();
        printStream.close();
    }

}
