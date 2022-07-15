package com.ob11to.dao;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class QPreidcate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static QPreidcate builder() {
        return new QPreidcate();
    }

    public <T> QPreidcate add(T object, Function<T, Predicate> function){
        if(object != null){
            predicates.add(function.apply(object));
        }
        return this;
    }

    public Predicate builderAnd() {
        return ExpressionUtils.allOf(predicates);
    }

    public Predicate builderOr() {
        return ExpressionUtils.anyOf(predicates);
    }
}
