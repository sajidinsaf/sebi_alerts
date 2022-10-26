package com.visibleai.sebi.report.builder.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.visibleai.sebi.model.VisitorEntry;

public class FrequentVisitorDetailTest {

    @Test
    public void testFrequentVisitorEntry() {
        VisitorEntry visitorEntry = new VisitorEntry();

        String visitorNumber = "1234567890";
        visitorEntry.setVisitorNumber(visitorNumber);
        String visitorName = "Ting Tong";
        visitorEntry.setVisitorName(visitorName);
        String toMeet = "Bing Bong";
        visitorEntry.setToMeet(toMeet);
        String timeIn = "22 Jan 2022 10:20am";
        visitorEntry.setTimeIn(timeIn);
        String visitorCompany = "ICICI Bank";
        visitorEntry.setVisitorCompany(visitorCompany);
        String accessCardId = "A-674/P";
        visitorEntry.setAccessCardId(accessCardId);

        FrequentVisitorDetail frequentVisitorDetail = new FrequentVisitorDetail(visitorEntry);

        assertEquals(visitorNumber, frequentVisitorDetail.getVisitorNumber());
        assertEquals(visitorName, frequentVisitorDetail.getVisitorName());
        assertEquals(toMeet, frequentVisitorDetail.getToMeet());
        assertEquals(timeIn, frequentVisitorDetail.getTimeIn());
        assertEquals(visitorCompany, frequentVisitorDetail.getVisitorCompany());
        assertEquals(accessCardId, frequentVisitorDetail.getAccessCardId());

    }

}
