package com.ob11to.transaction;

import com.ob11to.entity.Payment;
import com.ob11to.util.HibernateUtil;
import com.ob11to.util.TestDataImporter;

import javax.persistence.LockModeType;

public class TransactionRunner {

    public static void main(String[] args) {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession();
            var session1 = sessionFactory.openSession()){
//            TestDataImporter.importData(sessionFactory);
            var transaction = session.beginTransaction();
            session1.beginTransaction();

            var payment = session.find(Payment.class, 1L);
            payment.setAmount(payment.getAmount() + 10);

            var payment1 = session1.find(Payment.class, 1L);
            payment1.setAmount(payment1.getAmount() + 20);


            session.getTransaction().commit();
            session1.getTransaction().commit();
        }
    }
}
