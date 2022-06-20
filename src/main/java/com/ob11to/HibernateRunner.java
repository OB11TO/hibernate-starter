package com.ob11to;

import com.ob11to.converter.BirthdayConverter;
import com.ob11to.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
        configuration.addAttributeConverter(new BirthdayConverter()); //8Ln custom attribute converter (второй вариант использовать converter)
        configuration.registerTypeOverride(new JsonBinaryType()); //зарегистрировали тип jsonb
//        configuration.addAnnotatedClass(User.class); один из вариантов связать (либо mapping в cfg.xml)
        configuration.configure(); //Используйте сопоставления и свойства, указанные в ресурсе приложения с именем hibernate.cfg.xml

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();//начинаем транзакцию

//            var user = User.builder()
//                    .username("ivan@gmail.com")
//                    .firstname("Ivan")
//                    .lastname("Ivanov")
//                    .birthDate(new Birthday(LocalDate.of(2000, 12, 11)))
//                    .role(Role.USER)
//                    .info("""
//                            {
//                                "name": "Ivan",
//                                "age": 25
//                            }
//
//                            """)
//                    .build();

//            session.delete(user);

            var user1 = session.get(User.class, "ivan2@gmail.com");
            var user2 = session.get(User.class, "ivan2@gmail.com");

//            Dirty session - все изменения отразятся в базе данных
//            user1.setFirstname("Petrov");
//            session.flush();
//            session.isDirty();

            System.out.println(user1);
            System.out.println(user2);

            session.evict(user1); //удалить user1 из кеша
            session.clear(); //полностью отчистить кеш

            session.getTransaction().commit(); //закрываем транзакцию


        }
    }
}
