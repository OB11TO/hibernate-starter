package com.ob11to.dto;

import com.ob11to.entity.LocaleInfo;
import lombok.Value;

import java.util.List;

@Value
public class CompanyReadDto {

    Integer id;
    String name;
    List<LocaleInfo> locals;
}
