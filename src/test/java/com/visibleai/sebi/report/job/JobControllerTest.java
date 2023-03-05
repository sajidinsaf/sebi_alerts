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

    assertEquals(new DateUtil().asString(new Date(), Constants.JOB_ID_DEFAULT_DATE_FORMAT).substring(0, 8),
        new JobController().getId(new Properties()).substring(0, 8));

  }

}
