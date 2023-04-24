package com.jdms.grad.cities.application.service;

import com.jdms.grad.cities.application.mapper.CityMapper;
import com.jdms.grad.cities.application.model.CityDto;
import com.jdms.grad.cities.application.repository.CityRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class FileUploadService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    /**
     * scv file name from resources
     */
    @Value("${app.pull.csv.file.name}")
    private String fileName;

     public void uploadCSVFile() {
        try (Reader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName))))) {
            // create csv bean reader
            var csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CityDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            // convert
            List cityDtos = csvToBean.parse();
            AtomicInteger failedRows = new AtomicInteger();
            cityDtos.forEach(city ->
            {
                try {
                    cityRepository.save(cityMapper.mapToEntity((CityDto) city));
                } catch (Exception e) {
                    failedRows.getAndIncrement();
                    log.error("Failed to import city: {}", city, e);
                }
            });
            log.info("Rows failed: {}", failedRows.get());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
