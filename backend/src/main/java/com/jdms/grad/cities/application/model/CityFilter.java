package com.jdms.grad.cities.application.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class CityFilter {
    private String name;
}
