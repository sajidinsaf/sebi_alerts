package com.visibleai.sebi.validation.util;

import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Date> {

  @Override
  public int compare(Date o1, Date o2) {
    if (o1.getTime() == o2.getTime()) {
      return 0;
    }
    return o1.compareTo(o2);
  }

}