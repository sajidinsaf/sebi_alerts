package com.visibleai.sebi.report.job;

import java.util.Date;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.util.DateUtil;

@Component
public class JobController {

  public String getId(Properties config) {
    String jobType = config.getProperty(Constants.ALERT_JOB_TYPE);
    return jobType + "_" + (new DateUtil().asString(new Date(), Constants.JOB_ID_DEFAULT_DATE_FORMAT));
  }

}
