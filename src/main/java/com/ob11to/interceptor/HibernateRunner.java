package com.ob11to.interceptor;

import com.ob11to.entity.Company;
import com.ob11to.entity.Payment;
import com.ob11to.entity.User;
import com.ob11to.util.HibernateUtil;
import com.ob11to.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;

import javax.transaction.Transactional;
import java.util.List;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {

            User user = null;
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();

                user = session.find(User.class, 1L);
                var name = user.getCompany().getName();
                user.getUserChats().size();
                var user1 = session.find(User.class, 1L);

                var payment = session.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true)
//                        .setCacheRegion("name region")
//                        .setHint(QueryHints.HINT_CACHEABLE, true)
                        .list();

                session.getTransaction().commit();
            }
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();

                var user2 = session.find(User.class, 1L);
                var name = user2.getCompany().getName();
                user2.getUserChats().size();

                var payment = session.createQuery("select p from Payment p where p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true) //!!!
                        .list();

                session.getTransaction().commit();
            }
        }
    }
}
