package com.visibleai.sebi.test.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.visibleai.sebi.model.Constants;

@Configuration
@PropertySource("application.properties")
public class DatabaseConfig {

  @Autowired
  private Environment env;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty(Constants.PROPERTY_JDBC_DRIVER_CLASS));
    dataSource.setUrl(env.getProperty(Constants.PROPERTY_VAMS_DB_URL));
    dataSource.setUsername(env.getProperty(Constants.PROPERTY_VAMS_DB_USER));
    dataSource.setPassword(env.getProperty(Constants.PROPERTY_VAMS_DB_PASSWORD));

    return dataSource;
  }

  // @Bean
  public DataSource inMemDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:mem:bootapp;DB_CLOSE_DELAY=-1");
    dataSource.setUsername("sa");
    dataSource.setPassword("");

    return dataSource;
  }

  @Bean
  public JdbcTemplate inMemJdbcTemplate() {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(inMemDataSource());

    return jdbcTemplate;
  }

  // configure entityManagerFactory

  // configure transactionManager

  // configure additional Hibernate Properties
}