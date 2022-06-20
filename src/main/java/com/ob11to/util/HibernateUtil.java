package com.ob11to.util;

import com.ob11to.converter.BirthdayConverter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConverter()); //8Ln custom attribute converter (второй вариант использовать converter)
        configuration.registerTypeOverride(new JsonBinaryType()); //зарегистрировали тип jsonb
//        configuration.addAnnotatedClass(User.class); один из вариантов связать (либо mapping в cfg.xml)
        configuration.configure(); //Используйте сопоставления и свойства, указанные в ресурсе приложения с именем hibernate.cfg.xml

        return configuration.buildSessionFactory();
    }
}
