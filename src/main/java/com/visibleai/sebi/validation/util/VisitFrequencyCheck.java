package com.visibleai.sebi.validation.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.util.PermutationsUtil;

public class VisitFrequencyCheck {
  private int numberOfDays, frequencyViolationNumber;
  private Properties properties;
  private DateUtil dateUtil;
  private PermutationsUtil permutationsUtil;
  private String entryDateFormat;
  private FrequentVisitsDateFilter frequentVisitsDateFilter;
  private final DateToVisitorDetailsMapper dateToVisitorDetailsMapper;
  private FrequentVisitMapper frequentVisitMapper;

  public VisitFrequencyCheck(int numberOfDaysInThisPeriod, int frequencyViolationNumber, Properties properties,
      DateUtil dateUtil, PermutationsUtil permutationsUtil, FrequentVisitsDateFilter frequentVisitsDateFilter,
      DateToVisitorDetailsMapper dateToVisitorDetailsMapper, FrequentVisitMapper frequentVisitMapper) {
    numberOfDays = numberOfDaysInThisPeriod;
    this.frequencyViolationNumber = frequencyViolationNumber;
    this.properties = properties;
    this.dateUtil = dateUtil;
    this.permutationsUtil = permutationsUtil;
    this.frequentVisitsDateFilter = frequentVisitsDateFilter;
    this.dateToVisitorDetailsMapper = dateToVisitorDetailsMapper;
    this.frequentVisitMapper = frequentVisitMapper;
    entryDateFormat = properties.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT,
        Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);
  }

  /**
   * Returns all date ranges that are within the numberOfDaysInThisPeriod
   * 
   * @param frequentVisitorDetails
   * @return
   * @throws ParseException
   */
  public List<List<FrequentVisitorDetail>> check(List<FrequentVisitorDetail> frequentVisitorDetails) {

    List<Date> visitDates = new ArrayList<>();
    for (FrequentVisitorDetail frequentVisitorDetail : frequentVisitorDetails) {
      Date entryTime = dateUtil.parseDate(frequentVisitorDetail.getTimeIn(), entryDateFormat);
      visitDates.add(entryTime);
    }

    List<List<Date>> visitDatePermutations = permutationsUtil.getAllPermutations(visitDates, frequencyViolationNumber);

    List<List<Date>> frequentVisits = new ArrayList<>();

    // check if the permutations qualify as frequent visits
    for (List<Date> dateSet : visitDatePermutations) {
      if (areFrequentVisits(dateSet)) {
        frequentVisits.add(dateSet);
      }
    }

    // we may get back some dates in the same range as permutations so we filter
    // them out
    frequentVisits = frequentVisitsDateFilter.filterSameRange(frequentVisits);

    // if there are ranges that overlap then merge them
    frequentVisits = frequentVisitMapper.mergeFrequentVisits(frequentVisits);

    // map the timestamps back to visitor details.
    return dateToVisitorDetailsMapper.mapToFrequestVisitorDetail(frequentVisits, frequentVisitorDetails);

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
