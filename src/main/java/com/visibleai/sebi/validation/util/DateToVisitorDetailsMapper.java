package com.visibleai.sebi.validation.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;
import com.visibleai.sebi.util.DateUtil;

public class DateToVisitorDetailsMapper {

  private DateUtil dateUtil;
  private String entryDateFormat;

  public DateToVisitorDetailsMapper(DateUtil dateUtil, Properties config) {
    this.dateUtil = dateUtil;
    this.entryDateFormat = config.getProperty(Constants.PROPERTY_ENTRY_DATETIME_FORMAT,
        Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);
  }

  /**
   * 
   * @param frequentVisitDates
   * @param frequentVisitorDetails
   * @return
   */
  public List<List<FrequentVisitorDetail>> mapToFrequestVisitorDetail(List<List<Date>> frequentVisitDates,
      List<FrequentVisitorDetail> frequentVisitorDetails) {

    List<List<FrequentVisitorDetail>> frequentVisitsDetailsList = new ArrayList<>();

    for (List<Date> dates : frequentVisitDates) {
      List<FrequentVisitorDetail> frequentVisits = new ArrayList<>();

      dateIterator: for (Date date : dates) {
        for (FrequentVisitorDetail fvd : frequentVisitorDetails) {
          Date d = dateUtil.parseDate(fvd.getTimeIn(), entryDateFormat);
          // check if the date time matches the date time of the frequent visitor detail
          if (d.getTime() == date.getTime()) {
            frequentVisits.add(fvd);
            continue dateIterator;
          }
        }
      }

      frequentVisitsDetailsList.add(frequentVisits);
    }
    return frequentVisitsDetailsList;
  }
}
