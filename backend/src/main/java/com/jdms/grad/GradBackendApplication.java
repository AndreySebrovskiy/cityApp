package com.jdms.grad;

import com.jdms.grad.cities.application.appstart.AppStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = "com.jdms.grad")
public class GradBackendApplication  implements CommandLineRunner {

  @Autowired
  private AppStartService appStartService;

  public static void main(String[] args) {
    SpringApplication.run(GradBackendApplication.class, args);
  }

  @Override
  public void run(String... args) {
    appStartService.performAppStartActions();
  }
}
