package com.model;

import org.springframework.data.jpa.domain.Specification;

public class MovieSpecification {
    public static Specification<Movie> titleStartsWith(String prefix) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                prefix.toLowerCase() + "%");
    }
}
