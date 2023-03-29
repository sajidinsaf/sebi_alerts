package com.visibleai.sebi.test.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.util.DateUtil;

@Component
public class TestDataLoader {

  public void loadTestData(JdbcTemplate jdbcTemplate) {

    try {

      System.out.println("Creating in memory table for test data");
      jdbcTemplate.execute(
          "create table if not exists visitor_entry (id_proof varchar(200), location  varchar(200), division varchar(200), department varchar(200), visitor_name varchar(200), visitor_number varchar(200), access_card varchar(200), visitor_company varchar(200), to_meet varchar(200), remarks varchar(200), time_in datetime, time_out datetime, visit_duration varchar(200), logged_out varchar(200), host_l varchar(200), pass_id varchar(200), auth_by varchar(200), questions varchar(200), type_of_visitor varchar(200), assets varchar(200))");

      int count = 0;
      // try {
      count = jdbcTemplate.queryForObject("select count(*) as count from visitor_entry", Integer.class);
//      } catch (RuntimeException e) {
//        if (!e.getMessage().contains("No data is available")) {
//          throw e;
//        } else {
//          System.out.println("No data is available");
//        }
//      }
      if (count > 0) {
        System.out.println("Test data already loaded with " + count + " records");
        return;
      }

      String sampleDataFile = new File(".").getAbsolutePath() + "/src/main/resources/sample-visitor-record-1000.csv";

      BufferedReader bufferedReader = new BufferedReader(new FileReader(sampleDataFile));

      String insertQuery = "insert into visitor_entry (id_proof, location, division, department,visitor_name,visitor_number, access_card, visitor_company, to_meet, remarks, time_in, time_out, visit_duration, logged_out, host_l, pass_id, auth_by, questions, type_of_visitor, assets) values (";

      // bufferedReader.readLine();
      // Parse the file using the apache CSV library
      CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(bufferedReader);

      // Get the list of CSV records
      List<CSVRecord> csvRecords = parser.getRecords();

      System.out.println("Loading test data with " + csvRecords.size() + " records");

      for (CSVRecord csvRecord : csvRecords) {

        StringBuilder sb = new StringBuilder(insertQuery);
        for (int i = 0; i < 20; i++) {
          if (!sb.toString().endsWith("(")) {
            sb.append(",");
          }
          String value = csvRecord.get(i);

          // end_time is not filled into many columns
          if (i == 11 && value.trim().equals("")) {
            value = csvRecord.get(10);
          }
          if ((i == 10 || i == 11) && !value.trim().equals("")) {
//            if (!value.toUpperCase().endsWith("AM") && !value.toUpperCase().endsWith("PM")) {
//              value += "PM";
//            }
            Date date = new Date();
            try {
              date = new DateUtil().parseDate(value, Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);
              value = new DateUtil().asString(date, "yyyy-MM-dd HH.mm.ss");

            } catch (Exception e) {
              date = new DateUtil().parseDate(value, "dd/MM/yy HH:mm");
              value = new DateUtil().asString(date, "yyyy-MM-dd HH.mm.ss");
            }

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

  public void setupPeriodicData(JdbcTemplate jdbcTemplate) {
    String[] phones = new String[] { "6416627339", "4186561535", "7506029416", "9049953883", "4791482699", "5787303822",
        "2975098098", "7295583516", "7506029416", "3539890575" };
    for (int i = 1; i <= 10; i++) {
      String value = new DateUtil().asString(new Date(System.currentTimeMillis() - (i * 60)), "yyyy-MM-dd HH.mm.ss");
      jdbcTemplate
          .execute("update visitor_entry set time_in='" + value + "' where visitor_number='" + phones[i - 1] + "'");
    }
  }

  public static void main(String[] args) {
    JdbcTemplate jdbcTemplate = new DatabaseConfig().inMemJdbcTemplate();
    new TestDataLoader().loadTestData(jdbcTemplate);
  }
}
