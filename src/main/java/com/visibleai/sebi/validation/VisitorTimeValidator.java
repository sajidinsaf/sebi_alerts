package com.visibleai.sebi.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VisitorTimeValidator {
    public boolean isOutOfWorkingHours(String dayIn) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy hh:mma");

        Date date = simpleDateFormat.parse(dayIn);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfTheWeek == 1 || dayOfTheWeek == 7) {
            return true;
        }

        int hourOfTheDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minuteOfTheHour = calendar.get(Calendar.MINUTE);

        if (hourOfTheDay < 9 || hourOfTheDay > 17 || (hourOfTheDay == 17 && minuteOfTheHour > 0)) {
            return true;
        }

        return false;
    }

    public static void main(String args[]) throws ParseException {
        VisitorTimeValidator v = new VisitorTimeValidator();
        v.isOutOfWorkingHours("16 Oct 2022 11:39AM");
    }
}
