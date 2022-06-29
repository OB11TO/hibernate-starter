package com.ob11to;

import com.ob11to.entity.Birthday;
import com.ob11to.entity.Company;
import com.ob11to.entity.PersonalInfo;
import com.ob11to.entity.User;
import com.ob11to.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
        Company company = Company.builder()
                .name("Amazon")
                .build();

        User user = User.builder()
                .username("o@ob11to.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Berserk")
                        .lastname("Berserk1")
                        .birthDate(new Birthday(LocalDate.of(1000, 1, 1)))
                        .build())
                .build();
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                var transaction = session1.beginTransaction();
                log.trace("Transaction is created, {}", transaction);

                var company1 = session1.get(Company.class, 4);
                System.out.println("");


                session1.getTransaction().commit();
            }
            log.warn("User is in detached state: {}, session is closed {}", user, session1);
        } catch (Exception exception) {
            log.error("Exception occurred", exception);
            throw exception;
        }
    }
}
