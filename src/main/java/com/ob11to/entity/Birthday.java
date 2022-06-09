package com.ob11to.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Birthday {

   @Getter
   LocalDate birthDate;

   public long getAge(){
      return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
   }
}
