package com.visibleai.sebi.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;

public class VisitFrequencyCheck {
    private int numberOfDays;
    private Properties properties;

    public VisitFrequencyCheck(int numberOfDaysInThisPeriod, Properties properties) {
        numberOfDays = numberOfDaysInThisPeriod;
        this.properties = properties;
    }

    public boolean check(FrequentVisitorDetail frequentVisitorDetail) throws ParseException {
        String dateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String timeIn = frequentVisitorDetail.getTimeIn();
        Date entryTime = simpleDateFormat.parse(timeIn);
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
