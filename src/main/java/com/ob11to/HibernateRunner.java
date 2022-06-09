package com.ob11to;

import com.ob11to.entity.Role;
import com.ob11to.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
//        BlockingQueue<Connection> pool = null;
//        Connection connection = pool.take();
//        SessionFactory
//
//        Connection connection = DriverManager
//                .getConnection("db.url", "db.username", "db.password");
//        Session

        Configuration configuration = new Configuration();
        configuration.configure(); //Используйте сопоставления и свойства, указанные в ресурсе приложения с именем hibernate.cfg.xml

//        configuration.addAnnotatedClass(User.class); один из вариантов связать

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();//начинаем транзакцию

            var user = User.builder()
                    .username("ivan1@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .birthDate(LocalDate.of(2000,12,11))
                    .age(20)
                    .role(Role.ADMIN)
                    .build();

            session.persist(user); // сохраняет в бд???
            session.getTransaction().commit(); //закрываем транзакцию


        }
    }
}
