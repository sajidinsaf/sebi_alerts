package com.visibleai.sebi.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.visibleai.sebi.db.VisitorEntryDatabaseReader;
import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Orchestrator;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.web.model.RequestReportsForm;

@Controller
public class ThymeLeafController {

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/generateReports")
    public String getVisitorEntries(@ModelAttribute RequestReportsForm requestReportsForm, Model model,
            @Value("${vams.db.query}") String query, @Value("${jdbc.driver.class}") String driver,
            @Value("${vams.db.password}") String password, @Value("${vams.db.user}") String user,
            @Value("${vams.db.url}") String url, @Value("${broker.list.file}") String brokerList,
            @Value("${govt.org.list.file}") String governmentOrgList, @Value("${visitor.list.file}") String visitorList,
            @Value("${employee.match.list.file}") String employeeMatchList) throws IOException {

        System.out.println(brokerList);

        Properties properties = new Properties();
        properties.put(Constants.PROPERTY_VAMS_DB_QUERY, query);
        properties.put(Constants.PROPERTY_JDBC_DRIVER_CLASS, driver);
        properties.put(Constants.PROPERTY_VAMS_DB_PASSWORD, password);
        properties.put(Constants.PROPERTY_VAMS_DB_USER, user);
        properties.put(Constants.PROPERTY_VAMS_DB_URL, url);
        properties.put(Constants.PROPERTY_REPORT_FORM, requestReportsForm);
        properties.put(Constants.PROPERTY_BROKER_LIST_FILE, brokerList);
        properties.put(Constants.PROPERTY_GOVT_ORG_LIST_FILE, governmentOrgList);
        properties.put(Constants.PROPERTY_VISITOR_MATCH_LIST_FILE, visitorList);
        properties.put(Constants.PROPERTY_EMPLOYEE_MATCH_LIST_FILE, employeeMatchList);

        VisitorEntryDatabaseReader vedr = new VisitorEntryDatabaseReader();

        List<VisitorEntry> visitorEntries = vedr.getVisitorEntries(properties);
        model.addAttribute("visitorEntries", visitorEntries);
        Orchestrator orchestrator = new Orchestrator(properties, new DateUtil());
        orchestrator.generateReports(visitorEntries);

        System.out.println(requestReportsForm.getStartDate());
        System.out.println(requestReportsForm.getEndDate());
        return "generateReports";
    }
}
