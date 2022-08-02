package com.ob11to.dao;

import com.ob11to.entity.User;

import javax.persistence.EntityManager;


public class UserRepository extends BaseRepository<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
