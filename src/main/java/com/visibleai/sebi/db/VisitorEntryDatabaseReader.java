package com.visibleai.sebi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.web.model.RequestReportsForm;

public class VisitorEntryDatabaseReader {

    public VisitorEntryDatabaseReader() {

    }

    public List<VisitorEntry> getVisitorEntries(Properties properties) {

        List<VisitorEntry> visitorEntries = new ArrayList<VisitorEntry>();

        String url = properties.getProperty(Constants.PROPERTY_VAMS_DB_URL);
        String user = properties.getProperty(Constants.PROPERTY_VAMS_DB_USER);
        String password = properties.getProperty(Constants.PROPERTY_VAMS_DB_PASSWORD);

        try {
            String driverClass = properties.getProperty(Constants.PROPERTY_JDBC_DRIVER_CLASS);
            if (driverClass == null) {
                throw new RuntimeException("Cannot proceed without JDBC Driver. Please specify it with config property"
                        + Constants.PROPERTY_JDBC_DRIVER_CLASS);
            }
            try {
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to the database is Successful" + url);
            String query = properties.getProperty(Constants.PROPERTY_VAMS_DB_QUERY);

            RequestReportsForm requestReportsForm = (RequestReportsForm) properties.get(Constants.PROPERTY_REPORT_FORM);

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setDate(1, requestReportsForm.getStartDate());
            pstmt.setDate(2, requestReportsForm.getEndDate());

            ResultSet resultset = pstmt.executeQuery();

            while (resultset.next()) {
                VisitorEntry visitorEntry = new VisitorEntry();

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
                Date time_in = resultset.getDate("time_in");
                Date time_out = resultset.getDate("time_out");
                int visit_duration = resultset.getInt("visit_duration");
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

                // print the results
                /*
                 * System.out.
                 * format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s\n"
                 * , id_proof, location, division, department, visitor_name, visitor_number,
                 * access_card, visitor_company, to_meet, remarks, time_in, time_out,
                 * visit_duration, logged_out, host_l, pass_id, auth_by, questions,
                 * type_of_visitor, assets);
                 */

                visitorEntries.add(visitorEntry);
            }
            pstmt.close();
            connection.close();
        } catch (SQLException throwables) {
            // TODO Auto-generated catch block
            throwables.printStackTrace();
        }
        return visitorEntries;
    }

}
