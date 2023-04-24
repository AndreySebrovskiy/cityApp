package com.jdms.grad.cities.domain.mapper;

import com.jdms.grad.cities.application.model.CityFilter;
import com.jdms.grad.cities.domain.filter.CityDomainFilter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DomainFilterMapper {

    CityDomainFilter map(CityFilter filter);
}
