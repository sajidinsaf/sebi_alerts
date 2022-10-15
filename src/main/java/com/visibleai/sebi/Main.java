package com.visibleai.sebi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {

  public static void main(String[] args) throws Exception {

    String fileName = Main.class.getClassLoader().getResource("sample-visitor-record.csv").getFile();

    if (args != null && args.length > 0) {
      fileName = args[0];
    }
    File file = new File(fileName);

    BufferedReader reader = new BufferedReader(new FileReader(file));

    String line = null;

    while ((line = reader.readLine()) != null) {
      System.out.println(line);
    }

    reader.close();

  }

}
