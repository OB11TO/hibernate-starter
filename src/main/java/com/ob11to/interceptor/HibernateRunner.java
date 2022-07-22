package com.ob11to.interceptor;

import com.ob11to.entity.Payment;
import com.ob11to.util.HibernateUtil;
import com.ob11to.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;

public class HibernateRunner {

    @Transactional
    public static void main(String[] args) {
       try(var sessionFactory = HibernateUtil.buildSessionFactory();
           var session = sessionFactory
                   .withOptions()
                   .interceptor(new GlobalInterceptor())
                   .openSession()){

           TestDataImporter.importData(sessionFactory);
           session.beginTransaction();

           var payment = session.find(Payment.class, 1L);
           payment.setAmount(payment.getAmount() + 10);


           session.getTransaction().commit();
       }
    }
}
