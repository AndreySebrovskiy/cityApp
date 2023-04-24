package com.jdms.grad.cities.application.repository;

import com.jdms.grad.cities.domain.filter.CityDomainFilter;
import com.jdms.grad.cities.domain.model.City;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;

import static com.jdms.grad.cities.domain.model.QCity.city;

public class CityRepositoryImpl implements CityRepositoryCustom {
    @Autowired
    private EntityManager entityManager;
    private Querydsl queryDsl;
    private JPAQueryFactory jpaQueryFactory;

    @PostConstruct
    void setUp() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
        queryDsl = new Querydsl(entityManager, (new PathBuilderFactory()).create(City.class));
    }

    @Override
    public Page<City> findCities(Pageable pageable, CityDomainFilter cityFilter) {
        var query =
                jpaQueryFactory
                        .selectFrom(city)
                        .where(getCitiesByFilterPredicate(cityFilter));
        var content = queryDsl.applyPagination(pageable, query).fetch();

        return new PageImpl<>(content, pageable, query.fetchCount());
    }


    private Predicate getCitiesByFilterPredicate(CityDomainFilter cityFilter) {
        return FilterPredicate.builder()
                .should(StringUtils.isNoneBlank(cityFilter.getName()))
                .add(() -> city.name.like((cityFilter.getName())))
                .build();
    }

}
