package com.ob11to.interceptor;

import com.ob11to.entity.Company;
import com.ob11to.entity.Payment;
import com.ob11to.entity.User;
import com.ob11to.util.HibernateUtil;
import com.ob11to.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;

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

                session.getTransaction().commit();
            }
            try (var session = sessionFactory.openSession()) {
                session.beginTransaction();

                var user2 = session.find(User.class, 1L);
                var name = user2.getCompany().getName();
                user2.getUserChats().size();

                session.getTransaction().commit();
            }
        }
    }
}
