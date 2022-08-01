package com.ob11to.dao;

import com.ob11to.entity.BaseEntity;
import com.ob11to.entity.Payment;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Cleanup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> clazz;
    @Getter
    private final EntityManager entityManager;

    @Override
    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(K id) {
        entityManager.detach(id);
        entityManager.flush(); // необязательно
    }

    @Override
    public void update(E entity) {
        //  session.update(entity);
        entityManager.merge(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    @Override
    public List<E> findAll() {
        var criteriaQuery = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.from(clazz);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }
}
