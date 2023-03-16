package com.visibleai.sebi.validation.util;

import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.util.PermutationsUtil;

public class VisitFrequencyCheckFactory {

  private DateUtil dateUtil;

  public VisitFrequencyCheckFactory(DateUtil dateUtil) {
    this.dateUtil = dateUtil;
  }

  public VisitFrequencyCheck getVisitFrequencyCheck(Properties properties, int numberOfDaysInThisPeriod,
      int frequencyViolationNumber) {
    String entryDateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT,
        Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);
    VisitDatesPermutationsFilter visitDatesPermutationsFilter = new VisitDatesPermutationsFilter(properties, dateUtil,
        numberOfDaysInThisPeriod, frequencyViolationNumber);
    PermutationsUtil permutationsUtil = new PermutationsUtil(dateUtil, entryDateFormat, visitDatesPermutationsFilter);

    FrequentVisitsDateFilter frequentVisitsDateFilter = new FrequentVisitsDateFilter(new DateComparator());

    DateToVisitorDetailsMapper dateToVisitorDetailsMapper = new DateToVisitorDetailsMapper(dateUtil, properties);
    FrequentVisitMapper frequentVisitMapper = new FrequentVisitMapper();
    return new VisitFrequencyCheck(numberOfDaysInThisPeriod, frequencyViolationNumber, properties, dateUtil,
        permutationsUtil, frequentVisitsDateFilter, dateToVisitorDetailsMapper, frequentVisitMapper);
  }
}
