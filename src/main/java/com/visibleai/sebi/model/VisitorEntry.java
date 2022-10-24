package com.visibleai.sebi.model;

import org.apache.commons.csv.CSVRecord;

public class VisitorEntry {
// ID Proof,Location,Division,Department,Visitor Name,Visitor Number,Access Card,Visitor's Company,To Meet,Remarks,Time In,Time Out,Visit Duration,Logged Out,Host L,Pass Id,Auth By,Questions,Type Of Visitor,Assets

    private String idProof;
    private String location;
    private String division;
    private String department;
    private String visitorName;
    private String visitorNumber;
    private String accessCardId;
    private String visitorCompany;
    private String toMeet;
    private String remarks;
    private String timeIn;
    private String timeOut;
    private String visitDuration;
    private String loggedOut;
    private String hostL;
    private String passId;
    private String authBy;
    private String questions;
    private String typeOfVistor;
    private String assets;

    public VisitorEntry() {
    }

    public VisitorEntry(CSVRecord csvRecord) {
        idProof = csvRecord.get(0);
        location = csvRecord.get(1);
        division = csvRecord.get(2);
        department = csvRecord.get(3);
        visitorName = csvRecord.get(4);
        visitorNumber = csvRecord.get(5);
        accessCardId = csvRecord.get(6);
        visitorCompany = csvRecord.get(7);
        toMeet = csvRecord.get(8);
        remarks = csvRecord.get(9);
        timeIn = csvRecord.get(10);
        timeOut = csvRecord.get(11);
        visitDuration = csvRecord.get(12);
        loggedOut = csvRecord.get(13);
        hostL = csvRecord.get(14);
        passId = csvRecord.get(15);
        authBy = csvRecord.get(16);
        questions = csvRecord.get(17);
        typeOfVistor = csvRecord.get(18);
        assets = csvRecord.get(19);
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorNumber() {
        return visitorNumber;
    }

    public void setVisitorNumber(String visitorNumber) {
        this.visitorNumber = visitorNumber;
    }

    public String getAccessCardId() {
        return accessCardId;
    }

    public void setAccessCardId(String accessCardId) {
        this.accessCardId = accessCardId;
    }

    public String getVisitorCompany() {
        return visitorCompany;
    }

    public void setVisitorCompany(String visitorCompany) {
        this.visitorCompany = visitorCompany;
    }

    public String getToMeet() {
        return toMeet;
    }

    public void setToMeet(String toMeet) {
        this.toMeet = toMeet;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getVisitDuration() {
        return visitDuration;
    }

    public void setVisitDuration(String visitDuration) {
        this.visitDuration = visitDuration;
    }

    public String getLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(String loggedOut) {
        this.loggedOut = loggedOut;
    }

    public String getHostL() {
        return hostL;
    }

    public void setHostL(String hostL) {
        this.hostL = hostL;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getAuthBy() {
        return authBy;
    }

    public void setAuthBy(String authBy) {
        this.authBy = authBy;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getTypeOfVistor() {
        return typeOfVistor;
    }

    public void setTypeOfVistor(String typeOfVistor) {
        this.typeOfVistor = typeOfVistor;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

}
