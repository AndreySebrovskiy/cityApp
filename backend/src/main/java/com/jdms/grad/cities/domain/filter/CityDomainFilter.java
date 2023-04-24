package com.jdms.grad.cities.domain.filter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CityDomainFilter {
    private final String name;
}
