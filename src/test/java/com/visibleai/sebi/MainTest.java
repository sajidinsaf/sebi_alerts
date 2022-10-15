package com.visibleai.sebi;

import org.junit.Test;

public class MainTest {

  @Test
  public void test() throws Exception {

    String fileName = Main.class.getClassLoader().getResource("sample-visitor-record.csv").getFile();

    Main.main(new String[] { fileName });

  }

}
