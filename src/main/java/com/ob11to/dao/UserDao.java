package com.ob11to.dao;

import com.ob11to.dto.PaymentFilter;
import com.ob11to.entity.Payment;
import com.ob11to.entity.QCompany;
import com.ob11to.entity.QPayment;
import com.ob11to.entity.User;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;


import static com.ob11to.entity.QCompany.company;
import static com.ob11to.entity.QPayment.payment;
import static com.ob11to.entity.QUser.user;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
//        return session.createQuery("select u from User u ", User.class)
//                .list();
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .fetch();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<User> findByFirstName(Session session, String firstName) {
//        return session.createQuery("select u from User u " +
//                        "where personalInfo.firstname = :firstname", User.class)
//                .setParameter("firstname", firstName)
//                .list();
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.personalInfo.firstname.eq(firstName))
                .fetch();
    }

    /**
     * Возвращает первые {limit} сотрудников, упорядочных по дате рождения (в порядке возрастания)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
//        return session.createQuery("select u from User u " +
//                        "order by personalInfo.birthDate", User.class)
//                .setMaxResults(limit)
//                .list();
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .orderBy(user.personalInfo.birthDate.asc())
                .limit(limit)
                .fetch();

    }

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    public List<User> findAllByCompanyName(Session session, String companyName) {
//        return session.createQuery("select u from User u " +
//                        "join u.company c " +
//                        "where c.name = :companyName", User.class)
//                .setParameter("companyName", companyName)
//                .list();
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.company.name.eq(companyName))
                .fetch();
    }

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentByCompanyName(Session session, String companyName) {
//        return session.createQuery("select p from Payment p " +
//                        "join p.receiver u " +
//                        "join u.company c " +
//                        "where c.name = :companyName " +
//                        "order by u.personalInfo.firstname, p.amount", Payment.class)
//                .setParameter("companyName", companyName)
//                .list();
        return new JPAQuery<Payment>(session)
                .select(payment)
                .from(payment)
                .join(payment.receiver, user).fetchJoin()
                .join(user.company, company)
                .where(company.name.eq(companyName))
                .orderBy(user.personalInfo.firstname.asc())
                .orderBy(payment.amount.asc())
                .fetch();

    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastName(Session session, PaymentFilter filter) {
//        return session.createQuery("select avg(p.amount) from Payment p " +
//                        "join p.receiver u " +
//                        "where u.personalInfo.firstname = :firstname " +
//                        "and  u.personalInfo.lastname = :lastname", Double.class)
//                .setParameter("firstname", firstName)
//                .setParameter("lastname", lastName)
//                .uniqueResult();

//        List<Predicate> predicates = new ArrayList<>();
//        if(filter.getFirstName() != null){
//            predicates.add(user.personalInfo.firstname.eq(filter.getFirstName()));
//        }
//        if(filter.getLastName() != null){
//            predicates.add(user.personalInfo.lastname.eq(filter.getLastName()));
//        }
        var predicate = QPreidcate.builder()
                .add(filter.getFirstName(), user.personalInfo.firstname::eq)
                .add(filter.getLastName(), user.personalInfo.lastname::eq)
                .builderAnd();

        return new JPAQuery<User>(session)
                .select(payment.amount.avg())
                .from(payment)
                .join(payment.receiver, user)
                .where(predicate)
                .fetchOne();

    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<Tuple> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
//        return session.createQuery("select c.name, avg(p.amount) from Company c " +
//                        "join c.users u " +
//                        "join u.payments p " +
//                        "group by c.name " +
//                        "order by c.name", Object[].class)
//                .list();
        return new JPAQuery<Tuple>(session)
                .select(company.name, payment.amount.avg())
                .from(company)
                .join(company.users, user)
                .join(user.payments, payment)
                .groupBy(company.name)
                .orderBy(company.name.asc())
                .fetch();
    }

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
    public List<Tuple> isItPossible(Session session) {
//        return session.createQuery("select u, avg(p.amount) from User u " +
//                        "join u.payments p " +
//                        "group by u " +
//                        "having avg(p.amount) > (select avg(p.amount) from Payment p) " +
//                        "order by u.personalInfo.firstname", Object[].class)
//                .list();
        return new JPAQuery<Tuple>(session)
                .select(user, payment.amount.avg())
                .from(user)
                .join(user.payments, payment)
                .groupBy(user)
                .having(payment.amount.avg().gt(
                        new JPAQuery<Payment>(session)
                                .select(payment.amount.avg())
                                .from(payment)
                ))
                .orderBy(user.personalInfo.firstname.asc())
                .fetch();
    }


    public static UserDao getInstance() {
        return INSTANCE;
    }
}
