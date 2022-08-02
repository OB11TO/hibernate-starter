package com.ob11to.dto;

import com.ob11to.entity.Company;
import com.ob11to.entity.PersonalInfo;
import com.ob11to.entity.Role;
import lombok.Value;

@Value
public class UserReadDto {

    Long id;
    PersonalInfo personalInfo;
    String username;
    Role role;
    String info;
    CompanyReadDto company;
}
