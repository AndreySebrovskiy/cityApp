package com.jdms.grad.cities.application.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Jacksonized
public class CityDto {
    @NotBlank
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String photo;
}
