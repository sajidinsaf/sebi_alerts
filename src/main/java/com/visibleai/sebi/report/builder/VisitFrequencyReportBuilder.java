package com.visibleai.sebi.report.builder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visibleai.sebi.model.VisitorEntry;
import com.visibleai.sebi.report.Report;
import com.visibleai.sebi.report.builder.model.FrequentVisitorDetail;
import com.visibleai.sebi.validation.VisitFrequencyCheck;

public class VisitFrequencyReportBuilder implements ReportBuilder {

    private VisitFrequencyCheck sevenDayCheck;
    private VisitFrequencyCheck fourteenDayCheck;
    private VisitFrequencyCheck thirtyDayCheck;
    private Map<String, List<FrequentVisitorDetail>> sevenDayMap;
    private Map<String, List<FrequentVisitorDetail>> fourteenDayMap;
    private Map<String, List<FrequentVisitorDetail>> thirtyDayMap;

    public VisitFrequencyReportBuilder() {
        sevenDayCheck = new VisitFrequencyCheck(7);
        fourteenDayCheck = new VisitFrequencyCheck(14);
        thirtyDayCheck = new VisitFrequencyCheck(30);
        sevenDayMap = new HashMap<String, List<FrequentVisitorDetail>>();
        fourteenDayMap = new HashMap<String, List<FrequentVisitorDetail>>();
        thirtyDayMap = new HashMap<String, List<FrequentVisitorDetail>>();
    }

    public void build(VisitorEntry visitorEntry) {
        FrequentVisitorDetail frequentVisitorDetail = new FrequentVisitorDetail(visitorEntry);

        try {
            boolean sevenDayCheckResult = sevenDayCheck.check(frequentVisitorDetail);
            boolean fourteenDayCheckResult = fourteenDayCheck.check(frequentVisitorDetail);
            boolean thirtyDayCheckResult = thirtyDayCheck.check(frequentVisitorDetail);

            String phoneNumber = frequentVisitorDetail.getVisitorNumber();

            if (sevenDayCheckResult) {
                List<FrequentVisitorDetail> frequentVisitorDetailList = sevenDayMap.getOrDefault(phoneNumber,
                        new ArrayList<FrequentVisitorDetail>());
                frequentVisitorDetailList.add(frequentVisitorDetail);
                sevenDayMap.put(phoneNumber, frequentVisitorDetailList);
            }

            if (fourteenDayCheckResult) {
                List<FrequentVisitorDetail> frequentVisitorDetailList = fourteenDayMap.getOrDefault(phoneNumber,
                        new ArrayList<FrequentVisitorDetail>());
                frequentVisitorDetailList.add(frequentVisitorDetail);
                fourteenDayMap.put(phoneNumber, frequentVisitorDetailList);
            }
            if (thirtyDayCheckResult) {
                List<FrequentVisitorDetail> frequentVisitorDetailList = thirtyDayMap.getOrDefault(phoneNumber,
                        new ArrayList<FrequentVisitorDetail>());
                frequentVisitorDetailList.add(frequentVisitorDetail);
                thirtyDayMap.put(phoneNumber, frequentVisitorDetailList);
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Report getReport() {
        // TODO Auto-generated method stub
        return null;
    }

}
