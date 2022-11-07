package com.visibleai.sebi.validation;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;
import com.visibleai.sebi.util.DateUtil;

public class VisitFrequencyCheck {
  private int numberOfDays;
  private Properties properties;
  private DateUtil dateUtil;

  public VisitFrequencyCheck(int numberOfDaysInThisPeriod, Properties properties, DateUtil dateUtil) {
    numberOfDays = numberOfDaysInThisPeriod;
    this.properties = properties;
    this.dateUtil = dateUtil;
  }

  public boolean check(FrequentVisitorDetail frequentVisitorDetail) throws ParseException {
    String dateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT);

    Date entryTime = dateUtil.parseDate(frequentVisitorDetail.getTimeIn(), dateFormat);
    Date todaysDate = new Date();
    Calendar numberOfDaysAgo = Calendar.getInstance();
    numberOfDaysAgo.setTime(todaysDate);
    numberOfDaysAgo.add(Calendar.DATE, -numberOfDays);
    Date numberOfDaysAgoDate = numberOfDaysAgo.getTime();
    boolean isEntryTimeBeforeToday = todaysDate.after(entryTime);
    boolean isEntryTimeAfterStartDate = numberOfDaysAgoDate.before(entryTime);
    return isEntryTimeBeforeToday && isEntryTimeAfterStartDate; // if statement

  }

}
