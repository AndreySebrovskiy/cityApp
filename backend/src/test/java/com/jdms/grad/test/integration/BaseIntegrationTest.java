package com.jdms.grad.test.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.jdms.grad.cities.application.appstart.AppStartService;
import com.jdms.grad.cities.application.appstart.DefaultAppStartService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest
@TestExecutionListeners(
    listeners = DbUnitTestExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@DatabaseTearDown("classpath:clear-data.xml")
@ActiveProfiles("integration-test")
public abstract class BaseIntegrationTest {
}
