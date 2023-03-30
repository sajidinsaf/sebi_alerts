// https://www.baeldung.com/spring-boot-start
package com.visibleai.sebi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.visibleai.sebi.springboot.testmodel")
@EntityScan("com.visibleai.sebi.springboot.testmodel")
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);

  }

}