package com.visibleai.sebi.report.builder.model;

import java.io.Serializable;

import com.visibleai.sebi.model.VisitorEntry;

public class FrequentVisitorDetail implements Serializable {
    private static final long serialVersionUID = -8377656182800433553L;
    private String visitorNumber;
    private String visitorName;
    private String toMeet;
    private String timeIn;
    private String timeOut;
    private String visitorCompany;
    private String accessCardId;
    private String location;

    public FrequentVisitorDetail() {

    }

    public FrequentVisitorDetail(VisitorEntry visitorEntry) {
        visitorNumber = visitorEntry.getVisitorNumber();
        visitorName = visitorEntry.getVisitorName();
        toMeet = visitorEntry.getToMeet();
        timeIn = visitorEntry.getTimeIn();
        timeOut = visitorEntry.getTimeOut();
        visitorCompany = visitorEntry.getVisitorCompany();
        accessCardId = visitorEntry.getAccessCardId();
        location = visitorEntry.getLocation();
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

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "FrequentVisitorDetail [visitorNumber=" + visitorNumber + ", visitorName=" + visitorName + ", toMeet="
                + toMeet + ", timeIn=" + timeIn + ", timeOut=" + timeOut + ", visitorCompany=" + visitorCompany
                + ", accessCardId=" + accessCardId + ", location=" + location + "]";
    }

}
