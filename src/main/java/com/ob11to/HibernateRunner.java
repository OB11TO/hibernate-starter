package com.ob11to;

import com.ob11to.entity.User;
import com.ob11to.util.HibernateUtil;
import com.ob11to.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class HibernateRunner {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

//            var user = session.get(User.class, 1L);
//            System.out.println(user.getPayments());
//            System.out.println(user.getCompany());

            session.createQuery("select u from User u", User.class)
                            .list();

            session.getTransaction().commit();
        }
    }
}
