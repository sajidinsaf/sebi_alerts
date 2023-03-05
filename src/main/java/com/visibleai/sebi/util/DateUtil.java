package com.visibleai.sebi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {

  public DateUtil() {

  }

  public Date parseDate(String dateString, String dateFormat) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

    Date date;
    try {
      date = simpleDateFormat.parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException("Could not parse date string: " + dateString + " with date format " + dateFormat);
    }
    return date;
  }

  public String asString(Date date, String dateFormat) {
    if (date == null || dateFormat == null) {
      return null;
    }
    return new SimpleDateFormat(dateFormat).format(date);
  }

  public Date earlierDate(Date date1, Date date2) {
    if (date1.before(date2)) {
      return date1;
    }
    return date2;
  }

  public Date laterDate(Date date1, Date date2) {
    if (date1.before(date2)) {
      return date2;
    }
    return date1;
  }

  public boolean sameDate(Date date1, Date date2) {
    return date1.getTime() == date2.getTime();
  }

  public int daysBetween(Date startDate, Date endDate) {
    LocalDateTime start = convertToLocalDateTimeViaInstant(startDate);
    LocalDateTime end = convertToLocalDateTimeViaInstant(endDate);

    return (int) Duration.between(start, end).toDays();
  }

  public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
