package com.visibleai.sebi.report.builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;

public class VisitFrequencyCheck {
    private int numberOfDays;

    public VisitFrequencyCheck(int numberOfDaysInThisPeriod) {
        numberOfDays = numberOfDaysInThisPeriod;
    }

    public boolean check(FrequentVisitorDetail frequentVisitorDetail) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);
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
