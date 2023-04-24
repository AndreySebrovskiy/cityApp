package com.jdms.grad.cities.application.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterPredicate {

  public static FilterPredicateBuilder builder() {
    return new FilterPredicateBuilder();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class FilterPredicateBuilder {

    private final Set<FilterPredicateSegment> predicateSegments = new HashSet<>();

    public FilterPredicateSegment should(boolean condition) {
      var predicateSegment = new FilterPredicateSegment(condition, this);
      predicateSegments.add(predicateSegment);

      return predicateSegment;
    }

    public static class FilterPredicateSegment {

      private final FilterPredicateBuilder parent;
      private final boolean condition;
      private Supplier<Predicate> predicate;

      private FilterPredicateSegment(boolean condition, FilterPredicateBuilder parent) {
        this.condition = condition;
        this.parent = parent;
      }

      public FilterPredicateBuilder add(Supplier<Predicate> predicate) {
        this.predicate = predicate;

        return parent;
      }
    }

    public Predicate build() {
      return ExpressionUtils.allOf(
          predicateSegments.stream()
              .filter(predicateSegment -> predicateSegment.condition)
              .map(predicateSegment -> predicateSegment.predicate.get())
              .collect(Collectors.toSet()));
    }
  }
}
