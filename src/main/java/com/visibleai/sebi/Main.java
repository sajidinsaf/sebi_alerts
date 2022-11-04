package com.visibleai.sebi;

import com.visibleai.sebi.report.Orchestrator;

public class Main {

    public static void main(String[] args) throws Exception {

        Orchestrator orchestrator = new Orchestrator(
                "/Users/aarishois/Applications/Development/Projects/temp/Reports.txt");

        orchestrator.generateReports();

    }

}
