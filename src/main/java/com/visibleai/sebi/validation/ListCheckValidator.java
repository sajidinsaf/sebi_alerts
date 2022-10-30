package com.visibleai.sebi.validation;

import java.util.List;

import com.visibleai.sebi.model.VisitorEntry;

public class ListCheckValidator {

    private List<String> checkList;

    public ListCheckValidator(List<String> checkList) {
        this.checkList = checkList;
    }

    public boolean isInList(VisitorEntry visitorEntry) {
        String visitorCompany = visitorEntry.getVisitorCompany();
        boolean result = checkList.stream().anyMatch(visitorCompany::equalsIgnoreCase);
        return result;
    }
}
