package com.jdms.grad.cities.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Accessors;


@Entity(name = City.ENTITY)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class City extends BaseEntity {
    public static final String ENTITY = "city";

    @NotNull
    @Column(name = "external_id")
    private Integer externalId;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "image_url")
    private String imageUrl;

    public void update(String name,  String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
