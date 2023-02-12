package com.visibleai.sebi;

public class MainTest {

  // @Test
  public void test() throws Exception {

    String fileName = Main.class.getClassLoader().getResource("config.properties").getFile();

    String[] args = new String[] { fileName };
    Main.main(args);

  }

}
