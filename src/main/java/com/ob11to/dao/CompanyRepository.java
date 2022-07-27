package com.ob11to.dao;

import com.ob11to.entity.Company;
import org.hibernate.SessionFactory;

public class CompanyRepository extends BaseRepository<Integer, Company> {

    public CompanyRepository(SessionFactory sessionFactory) {
        super(Company.class, sessionFactory);
    }
}
