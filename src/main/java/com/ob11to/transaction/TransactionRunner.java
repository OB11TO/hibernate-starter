package com.ob11to.transaction;

import com.ob11to.entity.Payment;
import com.ob11to.util.HibernateUtil;
import com.ob11to.util.TestDataImporter;

import javax.persistence.LockModeType;

public class TransactionRunner {

    public static void main(String[] args) {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()){
//            TestDataImporter.importData(sessionFactory);
            var transaction = session.beginTransaction();

            var payment = session.find(Payment.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
//            payment.setAmount(payment.getAmount() + 10);


            session.getTransaction().commit();
        }
    }
}
