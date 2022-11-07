package com.visibleai.sebi.validation;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;
import com.visibleai.sebi.util.DateUtil;

public class VisitFrequencyCheckTest {

  @Test
  public void testSevenDaysAgoCheck() throws ParseException {

    int period = 7;
    Properties properties = new Properties();
    properties.setProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT, Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);
    VisitFrequencyCheck visitFrequencyCheck = new VisitFrequencyCheck(period, properties, new DateUtil());

    FrequentVisitorDetail visitBetweenPeriodStartAndEndDate = new FrequentVisitorDetail();

    Date todaysDate = new Date();
    Calendar numberOfDaysAgo = Calendar.getInstance();
    numberOfDaysAgo.setTime(todaysDate);
    numberOfDaysAgo.add(Calendar.DATE, -period + 3);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);

    String dateOfVisitBetweenPeriodStartAndEndDate = simpleDateFormat.format(numberOfDaysAgo.getTime());

    numberOfDaysAgo.setTime(todaysDate);
    numberOfDaysAgo.add(Calendar.DATE, -period - 3);

    String dateOfVisitBeforePeriodStartDate = simpleDateFormat.format(numberOfDaysAgo.getTime());

    numberOfDaysAgo.setTime(todaysDate);
    numberOfDaysAgo.add(Calendar.DATE, 10);

    String dateOfVisitAfterPeriodEndDate = simpleDateFormat.format(numberOfDaysAgo.getTime());

    visitBetweenPeriodStartAndEndDate.setTimeIn(dateOfVisitBetweenPeriodStartAndEndDate);

    FrequentVisitorDetail visitBeforePeriodStartDate = new FrequentVisitorDetail();
    visitBeforePeriodStartDate.setTimeIn(dateOfVisitBeforePeriodStartDate);

    FrequentVisitorDetail visitAfterPeriodEndDate = new FrequentVisitorDetail();
    visitAfterPeriodEndDate.setTimeIn(dateOfVisitAfterPeriodEndDate);

    boolean resultOfBetweenPeriod = visitFrequencyCheck.check(visitBetweenPeriodStartAndEndDate);
    boolean resultOfBeforePeriod = visitFrequencyCheck.check(visitBeforePeriodStartDate);
    boolean resultOfAfterPeriod = visitFrequencyCheck.check(visitAfterPeriodEndDate);

    assertEquals(true, resultOfBetweenPeriod);
    assertEquals(false, resultOfBeforePeriod);
    assertEquals(false, resultOfAfterPeriod);

  }

}
