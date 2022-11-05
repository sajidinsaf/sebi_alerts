package com.visibleai.sebi.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;

public class VisitorTimeValidator {

    private Properties properties;

    public VisitorTimeValidator(Properties properties) {
        this.properties = properties;
    }

    public boolean isOutOfWorkingHours(String dayIn) throws ParseException {
        String dateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        Date date = simpleDateFormat.parse(dayIn);

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
