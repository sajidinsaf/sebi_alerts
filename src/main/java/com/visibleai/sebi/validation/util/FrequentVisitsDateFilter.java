package com.visibleai.sebi.validation.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class FrequentVisitsDateFilter {

  private Comparator<Date> dateComparator;

  public FrequentVisitsDateFilter(Comparator<Date> dateComparator) {
    this.dateComparator = dateComparator;
  }

  public List<List<Date>> filterSameRange(List<List<Date>> listsOfDates) {
    List<List<Date>> frequentVisits = new ArrayList<>();

    for (List<Date> dates : listsOfDates) {
      // if there are any repetitions of dates in different permutations, we put them
      // together.
      checkForRepetitions(dates, frequentVisits);
    }

    return frequentVisits;
  }

  /**
   * 
   * @param dates          - A a list of dates to check for any date repetitions
   * @param frequentVisits - The list in which to add the consolidated list
   */
  private void checkForRepetitions(List<Date> dates, List<List<Date>> frequentVisits) {
    int matchFoundAtIndex = -1;

    // check if any of the dates in the date list is in another list
    matchingFor: for (int i = 0; i < frequentVisits.size(); i++) {
      List<Date> oneOfTheDates = frequentVisits.get(i);
      for (Date date1 : dates) {
        for (Date date2 : oneOfTheDates) {
          if (date1.getTime() == date2.getTime()) {
            // get the index for the matching list
            matchFoundAtIndex = i;
            break matchingFor;
          }
        }
      }
    }

    if (matchFoundAtIndex != -1) {
      // remove the matching list from the permutations
      List<Date> existingDates = frequentVisits.remove(matchFoundAtIndex);
      // Use a treeset to have a unique ordered set of dates.
      TreeSet<Date> fullSetOfDates = new TreeSet<Date>(dateComparator);

      // add dates from both lists into the set
      fullSetOfDates.addAll(existingDates);
      fullSetOfDates.addAll(dates);

      // put this new date list back in the same index
      frequentVisits.add(matchFoundAtIndex, new ArrayList<>(fullSetOfDates));
    } else {
      // if no match found, add these dates to the list
      frequentVisits.add(dates);
    }

  }
}
