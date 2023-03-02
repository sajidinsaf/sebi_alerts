package com.visibleai.sebi.report.fileloader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

public class LocalFileLoader implements FileLoader {

  @Override
  public List<String> loadFileLines(Properties config, String configPropertyName) {
    String filePath = config.getProperty(configPropertyName);
    return loadListFromFile(filePath);
  }

  private List<String> loadListFromFile(String fileName) {
    try {
      List<String> list = Files.readAllLines(new File(fileName).toPath(), Charset.defaultCharset());
      return list;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
