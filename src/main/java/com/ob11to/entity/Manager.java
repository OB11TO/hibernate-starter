package com.ob11to.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = "manager")
@Entity
public class Manager extends User {

    private String projectName;

    @Builder
    public Manager(Long id, PersonalInfo personalInfo, String username, Role role, String info, Company company,
                   Profile profile, List<UserChat> userChats, String projectName) {
        super(id, personalInfo, username, role, info, company, profile, userChats);
        this.projectName = projectName;
    }
}
