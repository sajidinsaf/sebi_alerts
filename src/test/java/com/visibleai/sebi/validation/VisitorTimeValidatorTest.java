package com.visibleai.sebi.validation;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class VisitorTimeValidatorTest {

    @Test
    public void testWorkingTime() throws ParseException {
        VisitorTimeValidator visitorTimeValidator = new VisitorTimeValidator();
        String dayIn = "21 Jul 2022 11:39am";
        boolean result = visitorTimeValidator.isOutOfWorkingHours(dayIn);
        assertEquals(false,result);
    }
    @Test
    public void testWeekend() throws ParseException {
        VisitorTimeValidator visitorTimeValidator = new VisitorTimeValidator();
        String dayIn = "30 Jul 2022 18:00pm";
        boolean result = visitorTimeValidator.isOutOfWorkingHours(dayIn);
        assertEquals(true,result);
    }
    @Test
    public void testOutOfHoursOnWeekday() throws ParseException {
        VisitorTimeValidator visitorTimeValidator = new VisitorTimeValidator();
        String dayIn = "21 Jul 2022 04:30am";
        boolean result = visitorTimeValidator.isOutOfWorkingHours(dayIn);
        assertEquals(true,result);
    }
}
