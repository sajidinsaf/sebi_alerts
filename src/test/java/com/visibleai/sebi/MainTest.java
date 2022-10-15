package com.visibleai.sebi;

import org.junit.Test;

public class MainTest {

  @Test
  public void test() throws Exception {

    String fileName = Main.class.getClassLoader().getResource("sample-visitor-record.csv").getFile();

    System.out.println(fileName);

    // String fileName =
    // "/Users/aarishois/Applications/Development/Projects/sebi_alerts/src/test/resources/sample-visitor-record.csv";

    Main.main(new String[] { fileName });

  }

}
