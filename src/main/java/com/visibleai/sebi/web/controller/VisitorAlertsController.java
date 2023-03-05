package com.visibleai.sebi.web.controller;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.visibleai.sebi.db.VisitorEntryDatabaseReader;
import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.web.model.RequestReportsForm;

//@RestController
@Controller
// vea - visitor entry alerts
@RequestMapping("/api/vea")
public class VisitorAlertsController {

  @Value("${spring.application.name}")
  String appName;

  @Autowired
  private VisitorEntryDatabaseReader visitorEntryDatabaseReader;

//  @GetMapping("/")
//  public String homePage(Model model) {
//    model.addAttribute("appName", appName);
//    return "index: " + appName;
//  }
//
//  @GetMapping("/hi/{name}")
//  public String hello(@PathVariable String name) {
//    return "home " + name;
//  }

  @GetMapping("/calc/{data}")
  public int getTotal(@PathVariable String data) {
    String number1 = data.substring(0, 1);
    System.out.println(number1);
    String number2 = data.substring(2, 3);
    System.out.println(number2);
    String sign = data.substring(1, 2);
    System.out.println(sign);
    if (sign.equals("+")) {
      return Integer.parseInt(number1) + Integer.parseInt(number2);
    }

    if (sign.equals("-")) {
      return Integer.parseInt(number1) - Integer.parseInt(number2);
    }
    if (sign.equals("*")) {
      return Integer.parseInt(number1) * Integer.parseInt(number2);
    }

    return Integer.MIN_VALUE;
  }

  @PostMapping(value = "/list")
  public String getVisitorEntries(@ModelAttribute RequestReportsForm requestReportsForm, Model model,
      @Value("${vams.db.query}") String query, @Value("${jdbc.driver.class}") String driver,
      @Value("${vams.db.password}") String password, @Value("${vams.db.user}") String user,
      @Value("${vams.db.url}") String url) {
    Properties properties = new Properties();
    properties.put(Constants.PROPERTY_VAMS_DB_QUERY, query);
    properties.put(Constants.PROPERTY_JDBC_DRIVER_CLASS, driver);
    properties.put(Constants.PROPERTY_VAMS_DB_PASSWORD, password);
    properties.put(Constants.PROPERTY_VAMS_DB_USER, user);
    properties.put(Constants.PROPERTY_VAMS_DB_URL, url);

    model.addAttribute("visitorEntries", visitorEntryDatabaseReader.getVisitorEntries(properties));
    return "list";
  }

  @GetMapping(value = "/index")
  public String index() {
    return "index";
  }

}
