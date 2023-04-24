package com.jdms.grad.cities.application.service;


import com.jdms.grad.cities.application.mapper.CityMapper;
import com.jdms.grad.cities.application.model.CityResponse;
import com.jdms.grad.cities.application.model.CityUpdateRequest;
import com.jdms.grad.cities.application.repository.CityRepository;
import com.jdms.grad.cities.domain.mapper.DomainFilterMapper;
import com.jdms.grad.cities.domain.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(SpringExtension.class)
@Import(CityService.class)
class CityServiceTest {

    @MockBean
    private DomainFilterMapper domainFilterMapper;
    @MockBean
    private CityRepository repository;
    @MockBean
    private CityMapper cityMapper;

    @Autowired
    private CityService service;

    @Test
    void testUpdate() {
        City city = getDefaultCity(0);
        given(repository.findById(any())).willReturn(Optional.of(city));

        service.updateCity(UUID.randomUUID(), CityUpdateRequest.builder().name(city.getName()).imageUrl(city.getImageUrl()).build());
        ArgumentCaptor<City> captor = ArgumentCaptor.forClass(City.class);
        Mockito.verify(repository, atLeastOnce()).save(captor.capture());
    }

    @Test
    void testGetCities() {
        given(repository.findCities(any(), any())).willReturn(getDefaultPageable());
        given(cityMapper.mapToResponse(any(Page.class))).willCallRealMethod();
        Page<CityResponse> actual = service.findCities( PageRequest.of(0, 20), null);
        assertThat(actual.stream().count()).isEqualTo(20);
        assertThat(actual.getTotalPages()).isEqualTo(1);
    }

    private City getDefaultCity(Integer externalId) {
        City result = City.builder().uuid(UUID.fromString("5fd90994-e045-11ed-b5ea-0242ac120002"))
                .externalId(externalId).name("testName" + externalId).imageUrl("testLink" + externalId).build();
        result.setExternalId(externalId);
        return result;
    }

    private Page<City> getDefaultPageable() {
        List<City> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            result.add(getDefaultCity(i));
        }
        return new PageImpl<>(result);
    }
}
