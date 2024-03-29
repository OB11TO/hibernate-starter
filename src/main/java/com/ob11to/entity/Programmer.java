package com.ob11to.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn
@Entity
public class Programmer extends User {

    private Language language;

//    @Builder
//    public Programmer(Long id, PersonalInfo personalInfo, String username, Role role, String info, Company company,
//                      Profile profile, List<UserChat> userChats, Language language) {
//        super(id, personalInfo, username, role, info, company, profile, userChats);
//        this.language = language;
//    }
}
