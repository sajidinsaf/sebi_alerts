package com.visibleai.sebi.validation;

import java.util.List;

import com.visibleai.sebi.model.VisitorEntry;

public class CompanyMatchValidator extends ListCheckValidator {

    public CompanyMatchValidator(List<String> checkList) {
        super(checkList);

    }

    @Override
    protected String getValueToCheck(VisitorEntry visitorEntry) {
        return visitorEntry.getVisitorCompany();
    }

}
