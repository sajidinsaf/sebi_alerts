package com.visibleai.sebi.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import org.springframework.jdbc.core.RowMapper;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.util.DateUtil;

public class VisitorEntryRowMapper implements RowMapper<VisitorEntry> {

  @Override
  public VisitorEntry mapRow(ResultSet resultset, int rowNum) throws SQLException {
    VisitorEntry visitorEntry = new VisitorEntry();

    try {

      String id_proof = resultset.getString("id_proof");
      String location = resultset.getString("location");
      String division = resultset.getString("division");
      String department = resultset.getString("department");
      String visitor_name = resultset.getString("visitor_name");
      String visitor_number = resultset.getString("visitor_number");
      String access_card = resultset.getString("access_card");
      String visitor_company = resultset.getString("visitor_company");
      String to_meet = resultset.getString("to_meet");
      String remarks = resultset.getString("remarks");
      Date time_in = new Date(resultset.getDate("time_in").getTime());

      Date time_out = new Date(resultset.getDate("time_out").getTime());

      int visit_duration = new Random().nextInt(5) + 1;
      String logged_out = resultset.getString("logged_out");
      String host_l = resultset.getString("host_l");
      String pass_id = resultset.getString("pass_id");
      String auth_by = resultset.getString("auth_by");
      String questions = resultset.getString("questions");
      String type_of_visitor = resultset.getString("type_of_visitor");
      String assets = resultset.getString("assets");

      visitorEntry.setIdProof(id_proof);
      visitorEntry.setLocation(location);
      visitorEntry.setDivision(division);
      visitorEntry.setDepartment(department);
      visitorEntry.setVisitorName(visitor_name);
      visitorEntry.setVisitorNumber(visitor_number);
      visitorEntry.setAccessCardId(access_card);
      visitorEntry.setVisitorCompany(visitor_company);
      visitorEntry.setToMeet(to_meet);
      visitorEntry.setRemarks(remarks);

      String timeInString = new DateUtil().asString(time_in, Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);

      visitorEntry.setTimeIn(timeInString);
      String timeOutString = new DateUtil().asString(time_out, Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);
      if (timeOutString == null) {
        timeOutString = timeInString;
      }

      visitorEntry.setTimeOut(timeOutString);
      String visitDurationString = Integer.toString(visit_duration);
      visitorEntry.setVisitDuration(visitDurationString);
      visitorEntry.setLoggedOut(logged_out);
      visitorEntry.setHostL(host_l);
      visitorEntry.setPassId(pass_id);
      visitorEntry.setAuthBy(auth_by);
      visitorEntry.setQuestions(questions);
      visitorEntry.setTypeOfVistor(type_of_visitor);
      visitorEntry.setAssets(assets);
    } catch (Exception e) {
      System.out.println("Exception while creating visitor entry from database row: " + rowNum + ". Exception message: "
          + e.getMessage());

    }
    return visitorEntry;
  }

}
