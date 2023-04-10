package com.visibleai.sebi.report.job;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Properties;

import org.junit.Test;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.util.DateUtil;

public class JobControllerTest {

  @Test
  public void jobIdIsCreatedFromDateUtils() {

    Properties config = new Properties();
    config.put(Constants.ALERT_JOB_TYPE, "test");
    assertEquals("test_" + (new DateUtil().asString(new Date(), Constants.JOB_ID_DEFAULT_DATE_FORMAT).substring(0, 8)),
        new JobController().getId(config).substring(0, 13));

  }

}
