package com.ob11to.dao;

import com.ob11to.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.*;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {

    E save(E entity);

    void delete(K id);

    void update(E entity);

    Optional<E> findById(K id, Map<String, Object> properties);

    default Optional<E> findById(K id) {
        return findById(id, emptyMap());
    }

    List<E> findAll();
}
