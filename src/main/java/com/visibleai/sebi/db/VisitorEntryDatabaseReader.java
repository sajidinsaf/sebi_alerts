package com.visibleai.sebi.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.web.model.RequestReportsForm;

@Component
public class VisitorEntryDatabaseReader {

  @Autowired
  private JdbcTemplate vamsJdbcTemplate;

  public VisitorEntryDatabaseReader() {

  }

  public List<VisitorEntry> getVisitorEntries(Properties properties) {

    String query = properties.getProperty(Constants.PROPERTY_VAMS_DB_QUERY);

    RequestReportsForm requestReportsForm = (RequestReportsForm) properties.get(Constants.PROPERTY_REPORT_FORM);

    List<VisitorEntry> visitorEntries = vamsJdbcTemplate.query(query, new PreparedStatementSetter() {
      public void setValues(PreparedStatement pstmt) throws SQLException {
        pstmt.setDate(1, requestReportsForm.getStartDate());
        pstmt.setDate(2, requestReportsForm.getEndDate());
      }
    }, new VisitorEntryRowMapper());

    System.out.println("Queried " + visitorEntries.size() + " visitor entries");
    return visitorEntries;

  }

}
