package com.visibleai.sebi.validation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.visibleai.sebi.model.VisitorEntry;

public class ListCheckValidatorTest {

  @Test
  public void testVisitorCompanyMatch() {
    List<String> checkList = Arrays.asList("xyz", "abc", "123", "MiG", "546");
    ListCheckValidator listCheckValidator = new CompanyMatchValidator(checkList);
    VisitorEntry visitorCompany = new VisitorEntry();
    visitorCompany.setVisitorCompany("MiG");
    boolean result = listCheckValidator.isInList(visitorCompany);
    assertEquals(true, result);

  }

  @Test
  public void testNonVisitorCompany() {
    List<String> checkList = Arrays.asList("xyz", "abc", "123", "MiG", "546");
    ListCheckValidator listCheckValidator = new CompanyMatchValidator(checkList);
    VisitorEntry visitorCompany = new VisitorEntry();
    visitorCompany.setVisitorCompany("Sukhoi");
    boolean result = listCheckValidator.isInList(visitorCompany);
    assertEquals(false, result);

  }

  @Test
  public void testEmployeeMatch() {
    List<String> checkList = Arrays.asList("xyz", "abc", "123", "MiG", "546");
    ListCheckValidator listCheckValidator = new EmployeeMatchValidator(checkList);
    VisitorEntry visitorCompany = new VisitorEntry();
    visitorCompany.setToMeetUsername("MiG");
    boolean result = listCheckValidator.isInList(visitorCompany);
    assertEquals(true, result);

  }

  @Test
  public void testVisitorCompanyCaseInsenitive() {
    List<String> checkList = Arrays.asList("xyz", "abc", "123", "MiG", "546");
    ListCheckValidator listCheckValidator = new CompanyMatchValidator(checkList);
    VisitorEntry visitorEntry = new VisitorEntry();
    visitorEntry.setVisitorCompany("mig");
    boolean result = listCheckValidator.isInList(visitorEntry);
    assertEquals(true, result);

  }

}
