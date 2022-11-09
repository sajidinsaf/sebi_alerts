package com.visibleai.sebi.validation;

public class AccessCardValidator {
  public boolean isMedia(String accessCardId) {
    boolean result = accessCardId.startsWith("M-");
    return result;
  }
}
