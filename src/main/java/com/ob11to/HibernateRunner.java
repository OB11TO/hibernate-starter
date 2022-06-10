package com.ob11to;

import com.ob11to.converter.BirthdayConverter;
import com.ob11to.entity.Birthday;
import com.ob11to.entity.Role;
import com.ob11to.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
//        BlockingQueue<Connection> pool = null;
//        Connection connection = pool.take();
//        SessionFactory
//
//        Connection connection = DriverManager
//                .getConnection("db.url", "db.username", "db.password");
//        Session

        Configuration configuration = new Configuration();
        configuration.configure(); //Используйте сопоставления и свойства, указанные в ресурсе приложения с именем hibernate.cfg.xml
        configuration.addAttributeConverter(new BirthdayConverter()); //8Ln custom attribute converter
        // configuration.registerTypeOverride(new JsonBinaryType()); //зарегистрировали тип jsonb
//        configuration.addAnnotatedClass(User.class); один из вариантов связать (либо mapping в cfg.xml)

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();//начинаем транзакцию

            var user = User.builder()
                    .username("ivan1@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .birthDate(new Birthday(LocalDate.of(2000, 12, 11)))
                    .role(Role.ADMIN)
                    .info("f")
                    .build();

            session.save(user);
            session.getTransaction().commit(); //закрываем транзакцию

        }
    }
}
