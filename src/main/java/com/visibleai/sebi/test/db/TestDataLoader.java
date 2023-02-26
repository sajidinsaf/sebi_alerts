package com.visibleai.sebi.test.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestDataLoader {

  public void loadTestData() {
    JdbcTemplate jdbcTemplate = new DatabaseConfig().inMemJdbcTemplate();

    try {

      System.out.println("Creating in memory table for test data");
      jdbcTemplate.execute(
          "create table visitor_entry (id_proof varchar(200), location  varchar(200), division varchar(200), department varchar(200), visitor_name varchar(200), visitor_number varchar(200), access_card varchar(200), visitor_company varchar(200), to_meet varchar(200), remarks varchar(200), time_in varchar(200), time_out varchar(200), visit_duration varchar(200), logged_out varchar(200), host_l varchar(200), pass_id varchar(200), auth_by varchar(200), questions varchar(200), type_of_visitor varchar(200), assets varchar(200))");

      String sampleDataFile = new File(".").getAbsolutePath() + "/src/main/resources/sample-visitor-record-1000.csv";

      BufferedReader bufferedReader = new BufferedReader(new FileReader(sampleDataFile));

      String insertQuery = "insert into visitor_entry (id_proof, location, division, department,visitor_name,visitor_number, access_card, visitor_company, to_meet, remarks, time_in, time_out, visit_duration, logged_out, host_l, pass_id, auth_by, questions, type_of_visitor, assets) values (";

      // bufferedReader.readLine();
      // Parse the file using the apache CSV library
      CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(bufferedReader);

      // Get the list of CSV records
      List<CSVRecord> csvRecords = parser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {

        StringBuilder sb = new StringBuilder(insertQuery);
        for (int i = 0; i < 20; i++) {
          if (!sb.toString().endsWith("(")) {
            sb.append(",");
          }
          String value = csvRecord.get(i);
          if (value.equals("")) {
            value = " ";
          }
          value = value.replaceAll("'", "");
          sb.append("'").append(value).append("'");
        }
        sb.append(")");
        jdbcTemplate.execute(sb.toString());
      }
      bufferedReader.close();
    } catch (Exception e) {
      System.out.println("Exception while creating test data: " + e.getMessage());
      e.printStackTrace();
    }

  }

  public static void main(String[] args) {
    new TestDataLoader().loadTestData();
  }
}
