package com.ob11to.mapper;

import com.ob11to.dao.CompanyRepository;
import com.ob11to.dto.UserCreateDto;
import com.ob11to.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final CompanyRepository companyRepository;

    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .personalInfo(object.getPersonalInfo())
                .username(object.getUsername())
                .info(object.getInfo())
                .role(object.getRole())
                .company(companyRepository.findById(object.getCompanyId()).orElseThrow(IllegalAccessError::new))
                .build();
    }
}
