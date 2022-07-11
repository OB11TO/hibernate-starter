package com.ob11to;

import javax.persistence.Column;
import javax.persistence.FlushModeType;
import javax.persistence.Table;

import com.ob11to.entity.*;
import com.ob11to.util.HibernateTestUtil;
import com.ob11to.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


class HibernateRunnerTest {

    @Test
    void checkHQL() {
        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            String name = "Ivan";
//            var result = session.createQuery(
//                            "select u from User u " +
//                                    "where u.personalInfo.firstname = ?1 ", User.class)
//                    .setParameter(1, name)
//                    .list();
            var result = session.createQuery("select u from User u " +
                            "join u.company c " +
                            "where u.personalInfo.firstname = :firstname and c.name = :name " +
                            "order by u.personalInfo.lastname desc ", User.class)
                    .setParameter("firstname", name)
                    .setParameter("name", "Google")
                    .setFlushMode(FlushModeType.AUTO)
                    .setHint(QueryHints.HINT_FETCH_SIZE, "50")
                    .list();

            var update = session.createQuery("update User u set u.role =  'ADMIN'")
                    .executeUpdate();

            var nativeQuery = session.createNativeQuery("select u.* from users u where u.firstname = 'Ivan'", User.class);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkTablePerClass() {
        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var yandex = Company.builder()
                    .name("yandex")
                    .build();
            session.save(yandex);

            var programmer = Programmer.builder()
                    .username("ivan@gmail.com")
                    .language(Language.JAVA)
                    .company(yandex)
                    .build();
            session.save(programmer);

            var manager = Manager.builder()
                    .username("sveta@gmail.com")
                    .projectName("Starter")
                    .company(yandex)
                    .build();
            session.save(manager);

            session.flush();

            session.clear();

            var programmer1 = session.get(Programmer.class, 1L);
            var user = session.get(User.class, 2L);
            System.out.println();


            session.getTransaction().commit();
        }
    }

    @Test
    void checkDockerTestContainers() {
        try (var sessionFactory = HibernateTestUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var com = Company.builder()
                    .name("Google")
                    .build();
            session.save(com);
            System.out.println("");

            session.getTransaction().commit();
        }
    }

    @Test
    void checkH2() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var company = Company.builder()
                    .name("qiwi")
                    .build();
            session.save(company);

            session.getTransaction().commit();
        }
    }

    @Test
    void mapsInMapping() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var company = session.get(Company.class, 5);

            company.getUsers().forEach((k, v) -> System.out.println(v));

            session.getTransaction().commit();
        }
    }

    @Test
    void collectionOrdering() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var company = session.get(Company.class, 5);

//            company.getUsers().forEach(System.out::println);

            session.getTransaction().commit();
        }
    }

    @Test
    void elementCollectionTest() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var company = session.get(Company.class, 3);
            company.getLocals().add(LocaleInfo.of("ru", "Описание на русском"));
            company.getLocals().add(LocaleInfo.of("en", "English description"));
            System.out.println(company.getLocals());

            session.getTransaction().commit();
        }
    }

    @Test
    void manyToManySeparateEntity() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = session.get(User.class, 10L);
            var chat = session.get(Chat.class, 2L);

            var userChat = UserChat.builder()
//                    .createdAt(Instant.now())
//                    .createdBy(user.getUsername())
                    .build();
            userChat.setUser(user);
            userChat.setChat(chat);

            session.save(userChat);
            System.out.println("");


            session.getTransaction().commit();
        }
    }

    @Test
    void manyToManyTest() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

//            var user = User.builder()
//                    .username("manyToManyYandex")
//                    .build();
            var chat = Chat.builder()
                    .name("yandex")
                    .build();

//            var user1 = session.get(User.class, 10L);
//            user.addChat(chat);
//            session.save(chat);
//
//            var chat1 = session.get(Chat.class, 2L);
//            user.addChat(chat1);
//            session.save(user);
//
//            System.out.println("");


            session.getTransaction().commit();
        }
    }

    @Test
    void oneToOnePrimaryKey() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

//            var user = User.builder()
//                    .username("oneToOne2")
//                    .build();
            var profile = Profile.builder()
                    .language("en")
                    .street("50 let")
                    .build();

//            profile.setUser(user);
//            session.save(user);
//            var user1 = session.get(User.class, 4L);
            System.out.println("");


            session.getTransaction().commit();
        }
    }

    @Test
    void orphanRemoval() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

//            company = session.get(Company.class, 7);
            Company company = session.getReference(Company.class, 8);
//            company.getUsers().removeIf(user -> user.getId().equals(6L));


            session.getTransaction().commit();
        }
    }

    @Test
    void lazyInitializationException() {
        Company company = null;
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

//            company = session.get(Company.class, 7);
            company = session.getReference(Company.class, 7);

            session.getTransaction().commit();
        }
        var users = company.getUsers();
        System.out.println(users.size());
    }

    @Test
    void manyToOneDeleteUser() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var user = session.get(User.class, 2L);
        session.delete(user);

        session.getTransaction().commit();
    }

    @Test
    void oneToManyDeleteCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 1);
        session.delete(company);

        session.getTransaction().commit();
    }

    @Test
    void oneToManyAddUserToNewCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = Company.builder()
                .name("vk")
                .build();
//        var user = User.builder()
//                .username("22@vk.ru")
//                .build();
//        var profile = Profile.builder()
//                .street("22")
//                .build();
//        profile.setUser(user);

//        var user2 = User.builder()
//                .username("11@vk.ru")
//                .build();
//        user.setCompany(company);
//        company.getUsers().add(user);

//        company.addUser(user);
//        company.addUser(user2);
        session.save(company);


        session.getTransaction().commit();
    }

    @Test
    void oneToMany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 1);
        System.out.println("");

        session.getTransaction().commit();

    }

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = null;

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