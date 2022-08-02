package com.ob11to.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
