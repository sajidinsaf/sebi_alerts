package com.visibleai.sebi.report.builder.model;

import com.visibleai.sebi.model.VisitorEntry;

public class FrequentVisitorDetail {
    private String visitorNumber;
    private String visitorName;
    private String toMeet;
    private String timeIn;
    private String visitorCompany;
    private String accessCardId;

    public FrequentVisitorDetail() {

    }

    public FrequentVisitorDetail(VisitorEntry visitorEntry) {
        visitorNumber = visitorEntry.getVisitorNumber();
        visitorName = visitorEntry.getVisitorName();
        toMeet = visitorEntry.getToMeet();
        timeIn = visitorEntry.getTimeIn();
        visitorCompany = visitorEntry.getVisitorCompany();
        accessCardId = visitorEntry.getAccessCardId();
    }

    public String getVisitorNumber() {
        return visitorNumber;
    }

    public void setVisitorNumber(String visitorNumber) {
        this.visitorNumber = visitorNumber;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getToMeet() {
        return toMeet;
    }

    public void setToMeet(String toMeet) {
        this.toMeet = toMeet;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getVisitorCompany() {
        return visitorCompany;
    }

    public void setVisitorCompany(String visitorCompany) {
        this.visitorCompany = visitorCompany;
    }

    public String getAccessCardId() {
        return accessCardId;
    }

    public void setAccessCardId(String accessCardId) {
        this.accessCardId = accessCardId;
    }
}
