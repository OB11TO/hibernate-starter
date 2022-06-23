package com.ob11to;

import com.ob11to.entity.Birthday;
import com.ob11to.entity.Company;
import com.ob11to.entity.PersonalInfo;
import com.ob11to.entity.User;
import com.ob11to.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
        Company company = Company.builder()
                .name("Kivi")
                .build();

        User user = User.builder()
                .username("berserk@ob11to.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Berserk")
                        .lastname("Berserk1")
                        .birthDate(new Birthday(LocalDate.of(1000, 1, 1)))
                        .build())
                .companyId(company)
                .build();
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                var transaction = session1.beginTransaction();
                log.trace("Transaction is created, {}", transaction);

              var user1 = session1.get(User.class, 2L);
//                session1.save(company);
//                session1.save(user);

                session1.getTransaction().commit();
            }
            log.warn("User is in detached state: {}, session is closed {}", user, session1);
        } catch (Exception exception) {
            log.error("Exception occurred", exception);
            throw exception;
        }
    }
}
