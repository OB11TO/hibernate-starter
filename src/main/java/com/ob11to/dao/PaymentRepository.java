package com.ob11to.dao;

import com.ob11to.entity.Payment;
import com.ob11to.entity.QPayment;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ob11to.entity.QPayment.*;

public class PaymentRepository extends BaseRepository<Long, Payment> {

    public PaymentRepository(EntityManager entityManager) {
        super(Payment.class, entityManager);
    }

    public List<Payment> findAllByReceiverId(Long receiverId){
        return new JPAQuery<Payment>(getEntityManager())
                .select(payment)
                .from(payment)
                .where(payment.receiver.id.eq(receiverId))
                .fetch();
    }
}
