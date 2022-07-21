package com.ob11to.interceptor;

import com.ob11to.entity.Payment;
import com.ob11to.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunner {
    public static void main(String[] args) {
       try(var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()){
           session.beginTransaction();

//           var session1 = sessionFactory
//                   .withOptions()
//                   .interceptor(new GlobalInterceptor())
//                   .openSession();
           var payment = session.find(Payment.class, 1L);
           payment.setAmount(payment.getAmount() + 10);


           session.getTransaction().commit();
       }
    }
}
