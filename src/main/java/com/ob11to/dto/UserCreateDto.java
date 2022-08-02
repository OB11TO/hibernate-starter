package com.ob11to.dto;

import com.ob11to.entity.PersonalInfo;
import com.ob11to.entity.Role;
import lombok.Value;

@Value
public class UserCreateDto {

    PersonalInfo personalInfo;
    String username;
    String info;
    Role role;
    Integer companyId;

}
