package com.ob11to.dao;

import com.ob11to.entity.Payment;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class PaymentRepository extends BaseRepository<Long, Payment> {

    public PaymentRepository(SessionFactory sessionFactory) {
        super(Payment.class, sessionFactory);
    }
}
