package com.ob11to.dao;

import com.ob11to.dto.UserCreateDto;
import com.ob11to.entity.PersonalInfo;
import com.ob11to.entity.Role;
import com.ob11to.mapper.CompanyReadMapper;
import com.ob11to.mapper.UserCreateMapper;
import com.ob11to.mapper.UserReadMapper;
import com.ob11to.service.UserService;
import com.ob11to.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDate;

public class HibernateRepositoryRunner {

    public static void main(String[] args) {
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()){

            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            session.beginTransaction();

            var userRepository = new UserRepository(session);

            var companyReadMapper = new CompanyReadMapper();
            var userReadMapper = new UserReadMapper(companyReadMapper);

            var companyRepository = new CompanyRepository(session);
            var userCreateMapper = new UserCreateMapper(companyRepository);
            var userService = new UserService(userRepository, userReadMapper, userCreateMapper);

            userService.findById(1L).ifPresent(System.out::println);

            UserCreateDto userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Liza")
                            .lastname("Liza")
                            .birthDate(LocalDate.now())
                            .build(),
                    "liza@gmail.com",
                    null,
                    Role.USER,
                    1
            );
            userService.create(userCreateDto);



            session.getTransaction().commit();
        }
    }
}
