package com.ob11to.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -3457789394493941971L;
    private String firstname;
    private String lastname;

//    @Convert(converter = BirthdayConverter.class) //один из вариантов как использовать convertor
//    @Column(name = "birth_date")
    private Birthday birthDate;
}
