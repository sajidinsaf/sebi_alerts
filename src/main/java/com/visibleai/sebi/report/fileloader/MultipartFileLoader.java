package com.visibleai.sebi.report.fileloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileLoader implements FileLoader {

  @Override
  public List<String> loadFileLines(Properties config, String configPropertyName) {
    MultipartFile file = (MultipartFile) config.get(configPropertyName);
    return loadListFromFile(file);
  }

  private List<String> loadListFromFile(MultipartFile file) {

    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
      List<String> list = new ArrayList<>();

      String line = null;

      while ((line = bufferedReader.readLine()) != null) {
        list.add(line);
      }
      bufferedReader.close();
      return list;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
