package com.ob11to.dao;

import com.ob11to.entity.Payment;
import com.ob11to.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    /**
     * Возвращает всех сотрудников
     */
    public List<User> findAll(Session session){
        return Collections.emptyList();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<User> findByFirstName(Session session, String firstName){
        return Collections.emptyList();
    }

    /**
     * Возвращает первые {limit} сотрудников, упорядочных по дате рождения (в порядке возрастания)
     */
    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit){
        return Collections.emptyList();
    }

    /**
     * Возвращает всех сотрудников компании с указанным названием
     */
    public List<User> findAllByCompanyName(Session session, String companyName){
        return Collections.emptyList();
    }

    /**
     * Возвращает все выплаты, полученные сотрудниками компании с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentByCompanyName(Session session, String companyName){
        return Collections.emptyList();
    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastName(Session session, String firstName, String lastName){
        return Double.MAX_VALUE;
    }

    /**
     * Возвращает для каждой компании: название, среднюю зарплату всех её сотрудников. Компании упорядочены по названию.
     */
    public List<Object[]> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session){
        return Collections.emptyList();
    }

    /**
     * Возвращает список: сотрудник (объект User), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
    public List<Object[]> isItPossible(Session session){
        return Collections.emptyList();
    }



    public static UserDao getInstance(){
        return INSTANCE;
    }
}
