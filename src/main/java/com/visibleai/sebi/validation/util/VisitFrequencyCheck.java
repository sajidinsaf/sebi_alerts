package com.visibleai.sebi.validation.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;
import com.visibleai.sebi.util.DateUtil;
import com.visibleai.sebi.util.PermutationsUtil;

public class VisitFrequencyCheck {

  private Logger logger = LoggerFactory.getLogger(VisitFrequencyCheck.class);

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

    logger.debug("Building permutations for dates: " + visitDates + ". Visit frequency violation number: " + 3);

    List<List<Date>> visitDatePermutations = permutationsUtil.getAllPermutations(visitDates, frequencyViolationNumber);

    if (logger.isDebugEnabled()) {
      logger.debug("Visit date permutations: " + visitDatePermutations);
    }
    List<List<Date>> frequentVisits = new ArrayList<>();

    // check if the permutations qualify as frequent visits
    for (List<Date> dateSet : visitDatePermutations) {
      // if (areFrequentVisits(dateSet)) {
      frequentVisits.add(dateSet);
      // }
    }

    // we may get back some dates in the same range as permutations so we filter
    // them out

    frequentVisits = frequentVisitsDateFilter.filterSameRange(frequentVisits);

    if (logger.isDebugEnabled()) {
      logger.debug("Visit date ranges after filtering for same range: " + frequentVisits);
    }
    // if there are ranges that overlap then merge them
    frequentVisits = frequentVisitMapper.mergeFrequentVisits(frequentVisits);

    // map the timestamps back to visitor details.
    return dateToVisitorDetailsMapper.mapToFrequestVisitorDetail(frequentVisits, frequentVisitorDetails);

  }

}
