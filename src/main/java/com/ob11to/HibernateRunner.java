package com.ob11to;

import com.ob11to.entity.User;
import com.ob11to.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunner {
    public static void main(String[] args) {
        User user = User.builder()
                .username("ob11to@mail.ru")
                .firstname("Petr")
                .lastname("Petrov")
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
           try(Session session1 = sessionFactory.openSession()){
               session1.beginTransaction();


               session1.getTransaction().commit();
           }
           try(Session session2 = sessionFactory.openSession()){
               session2.beginTransaction();

               user.setFirstname("GG-r");
               user.setUsername("GG-r");
//               session2.save(user);
//               session2.merge(user);
//               session2.refresh(user);



               session2.getTransaction().commit();
           }
        }
    }
}
