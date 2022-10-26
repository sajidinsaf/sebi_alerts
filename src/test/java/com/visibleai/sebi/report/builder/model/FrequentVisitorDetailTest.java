package com.visibleai.sebi.report.builder.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.visibleai.sebi.model.VisitorEntry;

public class FrequentVisitorDetailTest {

    @Test
    public void testFrequentVisitorEntry() {
        VisitorEntry visitorEntry = new VisitorEntry();

        String visitorNumber = "1234567890";
        String visitorName = "Ting Tong";
        String toMeet = "Bing Bong";
        String timeIn = "22 Jan 2022 10:20am";
        String visitorCompany = "ICICI Bank";
        String accessCardId = "A-674/P";

        FrequentVisitorDetail frequentVisitorDetail = new FrequentVisitorDetail(visitorEntry);

        assertEquals(visitorNumber, frequentVisitorDetail.getVisitorNumber());
        assertEquals(visitorName, frequentVisitorDetail.getVisitorName());
        assertEquals(toMeet, frequentVisitorDetail.getToMeet());
        assertEquals(timeIn, frequentVisitorDetail.getTimeIn());
        assertEquals(visitorCompany, frequentVisitorDetail.getVisitorCompany());
        assertEquals(accessCardId, frequentVisitorDetail.getAccessCardId());

    }

}
