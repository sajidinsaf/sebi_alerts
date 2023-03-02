package com.visibleai.sebi.report.fileloader;

import java.util.List;
import java.util.Properties;

public interface FileLoader {

  public List<String> loadFileLines(Properties config, String configPropertyName);

}
