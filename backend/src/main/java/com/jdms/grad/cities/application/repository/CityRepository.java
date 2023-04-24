package com.jdms.grad.cities.application.repository;

import com.jdms.grad.cities.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID>, CityRepositoryCustom,
        QuerydslPredicateExecutor<City> {
}
