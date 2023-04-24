package com.jdms.grad.cities.application.repository;

import com.jdms.grad.cities.domain.filter.CityDomainFilter;
import com.jdms.grad.cities.domain.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityRepositoryCustom {

    Page<City> findCities(Pageable pageable, CityDomainFilter cityFilter);
}
