package com.ob11to.dao;

import com.ob11to.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRepositoryRunner {

    public static void main(String[] args) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()){
            session.beginTransaction();

            var paymentRepository = new PaymentRepository(sessionFactory);
            paymentRepository.findById(1L).ifPresent(System.out::println);

            session.getTransaction().commit();
        }
    }
}
