package com.jdms.grad.cities.application.mapper;

import com.jdms.grad.cities.application.model.CityCreateRequest;
import com.jdms.grad.cities.application.model.CityDto;
import com.jdms.grad.cities.application.model.CityResponse;
import com.jdms.grad.cities.domain.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "externalId", source = "id")
    @Mapping(target = "imageUrl", source = "photo")
    City mapToEntity(CityDto dto);

    @Mapping(target = "externalId", source = "id")
    City mapToEntity(CityCreateRequest createRequest);

    List<City> mapToEntityList(List<CityDto> dtoList);

    @Mapping(target = "id", source = "uuid")
    CityResponse mapToResponse(City entity);

    List<CityResponse> mapToResponseList(List<City> entityList);

    default Page<CityResponse> mapToResponse(Page<City> cities) {
        return cities.map(this::mapToResponse);
    }
}
