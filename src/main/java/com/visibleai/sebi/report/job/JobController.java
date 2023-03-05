package com.visibleai.sebi.report.job;

import java.util.Date;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.visibleai.sebi.model.Constants;
import com.visibleai.sebi.util.DateUtil;

@Component
public class JobController {

  public String getId(Properties config) {
    return new DateUtil().asString(new Date(), Constants.JOB_ID_DEFAULT_DATE_FORMAT);
  }

}
