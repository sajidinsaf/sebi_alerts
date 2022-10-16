package com.visibleai.sebi.validation;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccessCardValidatorTest {

    @Test
    public void testNonMediaCard() {
        AccessCardValidator accessCardValidator = new AccessCardValidator();
        String accessCardId = "V-152/P";
        boolean result = accessCardValidator.isMedia(accessCardId);
        assertEquals(false,result);
    }
    
    @Test
    public void testIsMediaCard() {
        AccessCardValidator accessCardValidator = new AccessCardValidator();
        String accessCardId = "M-152/P";
        boolean result = accessCardValidator.isMedia(accessCardId);
        assertEquals(true,result);
    }
}
