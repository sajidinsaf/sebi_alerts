package com.visibleai.sebi.validation;

import java.util.List;

import com.visibleai.sebi.model.VisitorEntry;

public abstract class ListCheckValidator {

    private List<String> checkList;

    public ListCheckValidator(List<String> checkList) {
        this.checkList = checkList;
    }

    public boolean isInList(VisitorEntry visitorEntry) {
        String valueToCheck = getValueToCheck(visitorEntry);
        boolean result = checkList.stream().anyMatch(valueToCheck::equalsIgnoreCase);
        return result;
    }

    protected abstract String getValueToCheck(VisitorEntry visitorEntry);
}
