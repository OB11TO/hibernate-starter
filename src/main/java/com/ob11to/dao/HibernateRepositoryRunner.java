package com.ob11to.dao;

import com.ob11to.mapper.CompanyReadMapper;
import com.ob11to.mapper.UserReadMapper;
import com.ob11to.service.UserService;
import com.ob11to.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HibernateRepositoryRunner {

    public static void main(String[] args) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()){

            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            session.beginTransaction();

            var userRepository = new UserRepository(session);

            var companyReadMapper = new CompanyReadMapper();
            var userReadMapper = new UserReadMapper(companyReadMapper);

            var userService = new UserService(userRepository, userReadMapper);

            userService.findById(1L).ifPresent(System.out::println);


            session.getTransaction().commit();
        }
    }
}
