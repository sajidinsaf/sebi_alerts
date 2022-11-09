package com.visibleai.sebi.validation;

import java.util.List;

import com.visibleai.sebi.model.VisitorEntry;

public class VisitorMatchValidator extends ListCheckValidator {

  public VisitorMatchValidator(List<String> checkList) {
    super(checkList);
  }

  @Override
  protected String getValueToCheck(VisitorEntry visitorEntry) {
    return visitorEntry.getVisitorName();
  }

}
