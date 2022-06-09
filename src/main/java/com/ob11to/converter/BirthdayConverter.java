package com.ob11to.converter;

import com.ob11to.entity.Birthday;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Date;
import java.util.Optional;

@Converter(autoApply = true)
public class BirthdayConverter implements AttributeConverter<Birthday, java.sql.Date> {

    @Override
    public Date convertToDatabaseColumn(Birthday attribute) {
        return Optional.ofNullable(attribute)
                .map(Birthday::getBirthDate)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public Birthday convertToEntityAttribute(Date dbData) {
        return Optional.ofNullable(dbData)
                .map(Date::toLocalDate)
                .map(Birthday::new)
                .orElse(null);
    }
}
