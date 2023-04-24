package com.jdms.grad.cities.application.model;

import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


import java.util.UUID;

@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CityResponse {
    private UUID id;
    private Integer externalId;
    private String name;
    private String imageUrl;
}
