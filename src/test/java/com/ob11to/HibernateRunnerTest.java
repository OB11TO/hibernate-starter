package com.ob11to;

import com.ob11to.entity.Birthday;
import com.ob11to.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

class HibernateRunnerTest {

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .username("ivan@gmail.com")
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthDate(new Birthday(LocalDate.of(2000, 12, 11)))
                .build();

        String sql = "insert " +
                "into " +
                "%s " +
                "(%s) " +
                "values " +
                "(%s)";

        var tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] fields = user.getClass().getDeclaredFields();

        var columnNames = Arrays.stream(fields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(", "));

        String columnValues = Arrays.stream(fields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        System.out.printf((sql) + "%n", tableName, columnNames, columnValues);

        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(String.format(sql, tableName, columnNames, columnValues));
        for (Field field : fields) {
            field.setAccessible(true); //разрешить менять поля
            preparedStatement.setObject(1, field.get(user));
        }

    }

}