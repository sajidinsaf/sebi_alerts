package com.visibleai.sebi.web.controller;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.visibleai.sebi.db.VisitorEntryDatabaseReader;
import com.visibleai.sebi.model.Constants;

@Controller
public class ThymeLeafController {

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/list")
    public String getVisitorEntries(Model model, @Value("${vams.db.query}") String query,
            @Value("${jdbc.driver.class}") String driver, @Value("${vams.db.password}") String password,
            @Value("${vams.db.user}") String user, @Value("${vams.db.url}") String url) {
        Properties properties = new Properties();
        properties.put(Constants.PROPERTY_VAMS_DB_QUERY, query);
        properties.put(Constants.PROPERTY_JDBC_DRIVER_CLASS, driver);
        properties.put(Constants.PROPERTY_VAMS_DB_PASSWORD, password);
        properties.put(Constants.PROPERTY_VAMS_DB_USER, user);
        properties.put(Constants.PROPERTY_VAMS_DB_URL, url);
        VisitorEntryDatabaseReader vedr = new VisitorEntryDatabaseReader();
        model.addAttribute("visitorEntries", vedr.getVisitorEntries(properties));
        return "list";
    }
}
