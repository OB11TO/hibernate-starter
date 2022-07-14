package com.ob11to.dao;

import com.ob11to.dto.CompanyDto;
import com.ob11to.entity.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session) {
//        return session.createQuery("select u from User u ", User.class)
//                .list();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user);

        return session.createQuery(criteria)
                .list();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<User> findByFirstName(Session session, String firstName) {
//        return session.createQuery("select u from User u " +
//                        "where personalInfo.firstname = :firstname", User.class)
//                .setParameter("firstname", firstName)
//                .list();

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

//        criteria.select(user).where(
//                cb.equal(user.get("personalInfo").get("firstname"),firstName));
        criteria.select(user).where(
                cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstname), firstName));

        return session.createQuery(criteria)
                .list();
    }

    /**
     * Возвращает первые {limit} сотрудников, упорядочных по дате рождения (в порядке возрастания)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
//        return session.createQuery("select u from User u " +
//                        "order by personalInfo.birthDate", User.class)
//                .setMaxResults(limit)
//                .list();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user).orderBy(
                cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.birthDate)));

        return session.createQuery(criteria)
                .setMaxResults(limit)
                .list();

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
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);
        var company = user.join(User_.company);

        criteria.select(user).where(
                cb.equal(company.get(Company_.name), companyName)
        );

        return session.createQuery(criteria)
                .list();
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
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Payment.class);
        var payment = criteria.from(Payment.class);
        var user = payment.join(Payment_.receiver);
        var company = user.join(User_.company);

        criteria.select(payment).where(
                        cb.equal(company.get(Company_.name), companyName)
                )
                .orderBy(
                        cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstname)),
                        cb.asc(payment.get(Payment_.amount))
                );

        return session.createQuery(criteria)
                .list();
    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastName(Session session, String firstName, String lastName) {
//        return session.createQuery("select avg(p.amount) from Payment p " +
//                        "join p.receiver u " +
//                        "where u.personalInfo.firstname = :firstname " +
//                        "and  u.personalInfo.lastname = :lastname", Double.class)
//                .setParameter("firstname", firstName)
//                .setParameter("lastname", lastName)
//                .uniqueResult();

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Double.class);
        var payment = criteria.from(Payment.class);
        var user = payment.join(Payment_.receiver);

        List<Predicate> predicates = new ArrayList<>();
        if (firstName != null) {
            predicates.add(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstname), firstName));
        }
        if (lastName != null) {
            predicates.add(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.lastname), lastName));
        }

        criteria.select(cb.avg(payment.get(Payment_.amount))).where(
                predicates.toArray(Predicate[]::new)
        );

        return session.createQuery(criteria)
                .uniqueResult();
    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<CompanyDto> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
//        return session.createQuery("select c.name, avg(p.amount) from Company c " +
//                        "join c.users u " +
//                        "join u.payments p " +
//                        "group by c.name " +
//                        "order by c.name", Object[].class)
//                .list();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(CompanyDto.class);
        var company = criteria.from(Company.class);
        var users = company.join(Company_.users);
        var payment = users.join(User_.payments);

//        criteria.multiselect(
//                        company.get(Company_.name),
//                        cb.avg(payment.get(Payment_.amount)))
//                .groupBy(company.get(Company_.name))
//                .orderBy(
//                        cb.asc(company.get(Company_.name)));

        criteria.select(
                        cb.construct(CompanyDto.class,
                                company.get(Company_.name),
                                cb.avg(payment.get(Payment_.amount)))
                )
                .groupBy(company.get(Company_.name))
                .orderBy(cb.asc(company.get(Company_.name)));

        return session.createQuery(criteria)
                .list();
    }

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
    public List<Object[]> isItPossible(Session session) {
//        return session.createQuery("select u, avg(p.amount) from User u " +
//                        "join u.payments p " +
//                        "group by u " +
//                        "having avg(p.amount) > (select avg(p.amount) from Payment p) " +
//                        "order by u.personalInfo.firstname", Object[].class)
//                .list();

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Object[].class);
        var user = criteria.from(User.class);
        var payment = user.join(User_.payments);
        var subquery = criteria.subquery(Double.class);
        var paymentRoot = subquery.from(Payment.class);

        criteria.multiselect(user, cb.avg(payment.get(Payment_.amount)))
                .groupBy(user)
                .having(cb.gt(
                        cb.avg(payment.get(Payment_.amount)),
                        subquery.select(cb.avg(paymentRoot.get(Payment_.amount)))
                ))
                .orderBy(cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstname)));

        return session.createQuery(criteria)
                .list();
    }


    public static UserDao getInstance() {
        return INSTANCE;
    }
}
