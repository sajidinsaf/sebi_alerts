package com.visibleai.sebi.validation.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class FrequentVisitMapper {

  public FrequentVisitMapper() {
    // TODO Auto-generated constructor stub
  }

  public List<List<Date>> mergeFrequentVisits(List<List<Date>> frequentVisits) {
    Map<Long, Set<Date>> dateMap = new HashMap<>();

    for (List<Date> dates : frequentVisits) {
      Set<Date> frequentDateSet = findMatchingSet(dateMap, dates);

      for (Date date : dates) {
        frequentDateSet.add(date);
        dateMap.put(date.getTime(), frequentDateSet);
      }

    }

    List<List<Date>> result = new ArrayList<>();
    Set<Long> fromDate = new HashSet<>();
    for (Set<Date> aDateSet : dateMap.values()) {

      if (!fromDate.contains(aDateSet.iterator().next().getTime())) {
        result.add(new ArrayList<>(aDateSet));
        fromDate.add(aDateSet.iterator().next().getTime()); //
      }
    }
    return result;
  }

  private Set<Date> findMatchingSet(Map<Long, Set<Date>> dateMap, List<Date> dates) {

    for (Date date : dates) {
      if (dateMap.get(date.getTime()) != null) {
        return dateMap.get(date.getTime());
      }
    }

    return new TreeSet<>();
  }
}
