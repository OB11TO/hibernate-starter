package com.ob11to.mapper;

import com.ob11to.dto.CompanyReadDto;
import com.ob11to.entity.Company;
import com.ob11to.util.HibernateUtil;
import org.hibernate.Hibernate;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto mapFrom(Company object) {
        Hibernate.initialize(object.getLocals());
        return new CompanyReadDto(
                object.getId(),
                object.getName(),
                object.getLocals()
        );
    }
}
