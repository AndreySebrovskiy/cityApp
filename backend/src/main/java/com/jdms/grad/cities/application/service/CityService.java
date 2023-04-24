package com.jdms.grad.cities.application.service;

import com.jdms.grad.cities.application.exception.ObjectNotFoundException;
import com.jdms.grad.cities.application.model.*;
import com.jdms.grad.cities.domain.mapper.DomainFilterMapper;
import com.jdms.grad.cities.application.mapper.CityMapper;
import com.jdms.grad.cities.application.repository.CityRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Validated
@Transactional
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final DomainFilterMapper domainFilterMapper;

    public Page<CityResponse> findCities(@NotNull Pageable pageable, CityFilter cityFilter) {
        return cityMapper.mapToResponse(cityRepository.findCities(pageable, domainFilterMapper.map(cityFilter)));
    }

    public CityResponse createCity(@NotNull @NotNull CityCreateRequest createRequest) {
        return cityMapper.mapToResponse(cityRepository.save(cityMapper.mapToEntity(createRequest)));
    }

    public void updateCity(@NotNull UUID id, @NotNull CityUpdateRequest dto) {
        var cityEntity = cityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));
        cityEntity.update(dto.getName(), dto.getImageUrl());
        cityMapper.mapToResponse(cityRepository.save(cityEntity));
    }

    public void deleteCity(@NotNull UUID id) {
        var cityEntity = cityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));
        cityRepository.delete(cityEntity);
    }

    public CityResponse findOne(@NotNull UUID uuid) {
        return cityMapper.mapToResponse(cityRepository
                .findById(uuid).orElseThrow(() -> new ObjectNotFoundException(uuid)));
    }
}
