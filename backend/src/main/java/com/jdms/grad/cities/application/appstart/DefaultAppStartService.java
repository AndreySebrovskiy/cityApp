package com.jdms.grad.cities.application.appstart;

import com.jdms.grad.cities.application.service.FileUploadService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultAppStartService implements AppStartService {
    private final FileUploadService fileUploadService;
    /**
     * If true then during application startup database will be populated with csv file
     */
    @NotNull
    @Value("${app.pull.csv.file}")
    private Boolean pullCsvData;

    @Override
    public void performAppStartActions() {
        if (pullCsvData) {
            fileUploadService.uploadCSVFile();
        }

    }
}
