package com.ob11to.transaction;

import com.ob11to.util.HibernateUtil;

public class TransactionRunner {

    public static void main(String[] args) {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()){
            var transaction = session.beginTransaction();

            session.doWork(connection -> System.out.println(connection.getTransactionIsolation()));

            session.getTransaction().commit();
        }
    }
}
