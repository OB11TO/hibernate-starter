package com.ob11to;

import com.ob11to.entity.User;
import com.ob11to.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
        User user = User.builder()
                .username("obiito@ob11to.com")
                .firstname("ob11to")
                .lastname("uchiha")
                .build();
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
           try(session1){
               var transaction = session1.beginTransaction();
               log.trace("Transaction is created, {}", transaction);

                session1.saveOrUpdate(user);

               session1.getTransaction().commit();
           }
           log.warn("User is in detached state: {}, session is closed {}", user,session1);
        }catch (Exception exception){
            log.error("Exception occurred", exception);
            throw  exception;
        }
    }
}
