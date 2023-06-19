package com.jdms.grad.adapter.rest.cities;

import com.jdms.grad.cities.application.model.*;
import com.jdms.grad.cities.application.service.CityService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@OpenAPIDefinition(tags = {
        @Tag(name = "Cities", description = "REST API for cities operations")
})
@RestController
@RequestMapping(value = "/api/cities", produces=MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class CitiesRestController {
    private final CityService cityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponse createCity(@RequestBody CityCreateRequest request) {
        return cityService.createCity(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCity(@PathVariable UUID id, @RequestBody CityUpdateRequest cityUpdateRequest) {
        cityService.updateCity(id, cityUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable UUID id) {
        cityService.deleteCity(id);
    }

    @GetMapping
    public Page<CityResponse> findCities(Pageable pageable, CityFilter cityFilter) {
        return cityService.findCities(pageable, cityFilter);
    }

    @GetMapping("/{id}")
    public CityResponse getCity(@PathVariable UUID id) {
        return cityService.findOne(id);
    }

}
