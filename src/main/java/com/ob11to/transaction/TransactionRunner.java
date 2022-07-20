package com.ob11to.transaction;

import com.ob11to.entity.Payment;
import com.ob11to.util.HibernateUtil;
import com.ob11to.util.TestDataImporter;

import javax.persistence.LockModeType;

public class TransactionRunner {

    public static void main(String[] args) {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()){

            session.setDefaultReadOnly(true);
//            session.setReadOnly();

            session.beginTransaction();

            session.createNativeQuery("SET TRANSACTION READ ONLY;").executeUpdate();

            var payment = session.find(Payment.class, 1L);
            payment.setAmount(payment.getAmount() + 10);

            session.getTransaction().commit();
        }
    }
}
