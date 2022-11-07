package com.visibleai.sebi.validation;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.util.DateUtil;

public class VisitorTimeValidatorTest {

  Properties properties = null;

  @Before
  public void setUp() {
    properties = new Properties();
    properties.setProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT, Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);
  }

  @Test
  public void testWorkingTime() throws ParseException {

    VisitorTimeValidator visitorTimeValidator = new VisitorTimeValidator(properties, new DateUtil());
    String dayIn = "21 Jul 2022 11:39am";
    boolean result = visitorTimeValidator.isOutOfWorkingHours(dayIn);
    assertEquals(false, result);
  }

  @Test
  public void testWeekend() throws ParseException {
    VisitorTimeValidator visitorTimeValidator = new VisitorTimeValidator(properties, new DateUtil());
    String dayIn = "30 Jul 2022 18:00pm";
    boolean result = visitorTimeValidator.isOutOfWorkingHours(dayIn);
    assertEquals(true, result);
  }

  @Test
  public void testOutOfHoursOnWeekday_BeforeOfficeHours() throws ParseException {
    VisitorTimeValidator visitorTimeValidator = new VisitorTimeValidator(properties, new DateUtil());
    String dayIn = "21 Jul 2022 04:30am";
    boolean result = visitorTimeValidator.isOutOfWorkingHours(dayIn);
    assertEquals(true, result);
  }

  @Test
  public void testOutOfHoursOnWeekday_AfterOfficeHours() throws ParseException {
    VisitorTimeValidator visitorTimeValidator = new VisitorTimeValidator(properties, new DateUtil());
    String dayIn = "21 Jul 2022 17:30pm";
    boolean result = visitorTimeValidator.isOutOfWorkingHours(dayIn);
    assertEquals(true, result);
  }
}
