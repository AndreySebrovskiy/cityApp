package com.jdms.grad.cities.application.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Jacksonized
public class CityCreateRequest {
    @NotBlank
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String imageUrl;
}
