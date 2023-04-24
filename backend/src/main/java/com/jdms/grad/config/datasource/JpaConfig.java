package com.jdms.grad.config.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfigureBefore(JpaRepositoriesAutoConfiguration.class)
@Configuration
@EnableJpaRepositories(basePackages = "com.jdms.grad")
@RequiredArgsConstructor
public class JpaConfig {
}
