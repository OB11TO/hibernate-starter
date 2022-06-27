package com.ob11to;

import com.ob11to.entity.Birthday;
import com.ob11to.entity.Company;
import com.ob11to.entity.User;
import javax.persistence.Column;
import javax.persistence.Table;

import com.ob11to.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    void manyToOneDeleteUser(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 7);
        session.delete(company);

        session.getTransaction().commit();
    }

    @Test
    void oneToManyDeleteCompany(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 1);
        session.delete(company);

        session.getTransaction().commit();
    }

    @Test
    void oneToManyAddUserToNewCompany(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = Company.builder()
                .name("google")
                .build();

        var user = User.builder()
                .username("2@g.ru")
                .build();

        var user2 = User.builder()
                .username("1@g.ru")
                .build();
//        user.setCompany(company);
//        company.getUsers().add(user);
        company.addUser(user);
        company.addUser(user2);

        session.save(company);


        session.getTransaction().commit();
    }

    @Test
    void oneToMany(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 1);
        System.out.println("");

        session.getTransaction().commit();

    }

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .build();

        String sql = "insert " +
                "into " +
                "%s " +
                "(%s) " +
                "values " +
                "(%s)";

        String testSql = """
                INSERT
                INTO
                %s
                (%s)
                values
                (%s)
                """;

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

        System.out.printf((testSql) + "%n", tableName, columnNames, columnValues);

        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(String.format(sql, tableName, columnNames, columnValues));
        for (Field field : fields) {
            field.setAccessible(true); //разрешить менять поля
            preparedStatement.setObject(1, field.get(user));
        }

    }

}