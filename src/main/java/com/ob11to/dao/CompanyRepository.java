package com.ob11to.dao;

import com.ob11to.entity.Company;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;

public class CompanyRepository extends BaseRepository<Integer, Company> {

    public CompanyRepository(EntityManager entityManager) {
        super(Company.class, entityManager);
    }
}
