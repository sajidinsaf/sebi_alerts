package com.visibleai.sebi.validation;

import java.util.List;

import com.visibleai.sebi.model.VisitorEntry;

public class EmployeeMatchValidator extends ListCheckValidator {

    public EmployeeMatchValidator(List<String> checkList) {
        super(checkList);
    }

    @Override
    protected String getValueToCheck(VisitorEntry visitorEntry) {
        return visitorEntry.getToMeet();
    }

}
