package com.visibleai.sebi.validation;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.util.DateUtil;

public class VisitorTimeValidator {

  private Properties properties;
  private DateUtil dateUtil;

  public VisitorTimeValidator(Properties properties, DateUtil dateUtil) {
    this.properties = properties;
    this.dateUtil = dateUtil;
  }

  public boolean isOutOfWorkingHours(String dayIn) {
    String dateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT);

    Date date = dateUtil.parseDate(dayIn, dateFormat);

    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);

    int dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);

    if (dayOfTheWeek == 1 || dayOfTheWeek == 7) {
      return true;
    }

    int hourOfTheDay = calendar.get(Calendar.HOUR_OF_DAY);
    int minuteOfTheHour = calendar.get(Calendar.MINUTE);

    if (hourOfTheDay < 8 || (hourOfTheDay == 8 && minuteOfTheHour < 30) || hourOfTheDay > 19) {
      return true;
    }

    return false;
  }
}
