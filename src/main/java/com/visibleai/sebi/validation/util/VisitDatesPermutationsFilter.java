package com.visibleai.sebi.validation.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.util.PermutationsFilter;

public class VisitDatesPermutationsFilter implements PermutationsFilter {

  private Logger logger = LoggerFactory.getLogger(VisitDatesPermutationsFilter.class);

  private Properties config;
  private DateUtil dateUtil;
  private final int frequencyViolationNumber;
  private final int numberOfDays;

  public VisitDatesPermutationsFilter(Properties config, DateUtil dateUtil, int numberOfDays,
      int frequencyViolationNumber) {
    this.config = config;
    this.dateUtil = dateUtil;
    this.numberOfDays = numberOfDays;
    this.frequencyViolationNumber = frequencyViolationNumber;
  }

  @Override
  public boolean include(String[] perumrationsArray) {
    if (logger.isDebugEnabled()) {
      logger.debug("Checking whether to include " + Arrays.asList(perumrationsArray));
    }
    String entryDateFormat = config.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT,
        Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);
    if (perumrationsArray.length < frequencyViolationNumber) {
      if (logger.isDebugEnabled()) {
        logger.debug("Permutations array length is " + perumrationsArray.length + " which is less than: "
            + frequencyViolationNumber + " so it will not be included");
      }
      return false;
    }
    List<Date> datesList = new ArrayList<>();
    for (String dateString : perumrationsArray) {
      datesList.add(dateUtil.parseDate(dateString, entryDateFormat));
    }
    Collections.sort(datesList);
    boolean result = areFrequentVisits(datesList);
    if (!result && logger.isDebugEnabled()) {
      logger.debug("Permutations array " + Arrays.asList(perumrationsArray)
          + " doesn't contain frequent visits so it will not be included");
    } else if (logger.isDebugEnabled()) {
      logger.debug("Permutations array " + Arrays.asList(perumrationsArray) + " will be included");
    }
    return result;
  }

  /**
   * We expect and ordered list of dates. We check if the different between the
   * two dates is the less than or equal to the evaluation period.
   * 
   * @param dateSet
   * @return
   */
  private boolean areFrequentVisits(List<Date> dateSet) {
    return dateUtil.daysBetween(dateSet.get(0), dateSet.get(dateSet.size() - 1)) <= numberOfDays;
  }

}
