package com.visibleai.sebi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.visibleai.sebi.model.Constants;

public class PermutationsUtil {

  private DateUtil dateUtil;
  private String dateFormat;

  public PermutationsUtil(DateUtil dateUtil, String dateFormat) {
    this.dateUtil = dateUtil;
    this.dateFormat = dateFormat;
  }

  public static void main(String[] args) {
    Date[] array = new Date[] { new Date(2022, 10, 01), new Date(2022, 10, 02), new Date(2022, 10, 03),
        new Date(2022, 10, 04) };

    PermutationsUtil permutationsUtil = new PermutationsUtil(new DateUtil(),
        Constants.DEFAULT_VISITOR_ENTRY_DATE_FORMAT);

    List<String> permutations = permutationsUtil.uniquePermutationsForAllLengths(array);

    for (String permutation : permutations) {
      System.out.println(permutation);
    }
    System.out.println("=============================");
    System.out.println("=============================");
    System.out.println();
    List<List<Date>> resultsList = permutationsUtil.getAllPermutations(Arrays.asList(array), 3);

    for (List<Date> result : resultsList) {
      System.out.println(result);
    }

  }

  public List<List<Date>> getAllPermutations(List<Date> dates, int minSize) {
    Object[] objArray = dates.toArray();
    Date[] dateArray = new Date[objArray.length];

    for (int i = 0; i < objArray.length; i++) {
      dateArray[i] = (Date) objArray[i];
    }

    List<String> permutations = uniquePermutationsForAllLengths(dateArray);

    List<List<Date>> result = new ArrayList<>();
    for (String permutationsString : permutations) {
      String[] perumrationsArray = permutationsString.split(",");
      if (perumrationsArray.length < minSize) {
        continue;
      }
      List<Date> datesList = new ArrayList<>();
      for (String dateString : perumrationsArray) {
        datesList.add(dateUtil.parseDate(dateString, dateFormat));
      }

      Collections.sort(datesList);

      result.add(datesList);
    }

    return result;
  }

  List<String> uniquePermutationsForAllLengths(Date[] array) {
    List<String> result = new ArrayList<>();
    int lastIdx = array.length - 1;
    if (lastIdx >= 0) {
      result.add(format(array[lastIdx]));

      if (lastIdx > 0) {
        Date[] shorterArray = Arrays.copyOf(array, lastIdx);
        List<String> subResult = uniquePermutationsForAllLengths(shorterArray);
        result.addAll(subResult);
        for (String s : subResult) {
          result.add(s + "," + format(array[lastIdx]));
        }
      }
    }

    return result;
  }

  private String format(Date date) {
    return dateUtil.asString(date, dateFormat);
  }
}