package com.visibleai.sebi.report.builder;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;

public class VisitFrequencyCheckTest {

    @Test
    public void testSevenDaysAgoCheck() throws ParseException {

        int period = 7;
        VisitFrequencyCheck visitFrequencyCheck = new VisitFrequencyCheck(period);

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

    public void testEntryDays() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String todaysDay = "27/10/2022";
        String entryDay1 = "20/10/2022";
        String entryDay2 = "23/10/2022";
        String entryDay3 = "25/10/2022";
        String startDate = "20/10/2022";

        // convert String to LocalDate
        LocalDate reportDay = LocalDate.parse(todaysDay, formatter);
        LocalDate dateOfEntry1 = LocalDate.parse(entryDay1, formatter);
        LocalDate dateOfEntry2 = LocalDate.parse(entryDay2, formatter);
        LocalDate dateOfEntry3 = LocalDate.parse(entryDay3, formatter);
        LocalDate startDay = LocalDate.parse(startDate, formatter);

        assertEquals(reportDay, reportDay.isAfter(dateOfEntry1));
        assertEquals(startDay, reportDay.isBefore(dateOfEntry1));

        assertEquals(reportDay, reportDay.isAfter(dateOfEntry2));
        assertEquals(startDay, reportDay.isBefore(dateOfEntry2));

        assertEquals(reportDay, reportDay.isAfter(dateOfEntry3));
        assertEquals(startDay, reportDay.isBefore(dateOfEntry3));
    }

}
