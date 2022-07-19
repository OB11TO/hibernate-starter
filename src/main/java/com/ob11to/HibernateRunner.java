package com.ob11to;

import com.ob11to.entity.User;
import com.ob11to.entity.UserChat;
import com.ob11to.util.HibernateUtil;
import com.ob11to.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;

import java.awt.font.GraphicAttribute;
import java.util.Map;


public class HibernateRunner {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var userGraph = session.createEntityGraph(User.class);
            userGraph.addAttributeNodes("company", "userChats");
            var userChatsSub = userGraph.addSubgraph("userChats", UserChat.class);
            userChatsSub.addAttributeNodes("chat");

            Map<String, Object> properties = Map.of(
//                    GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCompanyAndUserChat")
                    GraphSemantic.LOAD.getJpaHintName(), userGraph
            );

            var user = session.find(User.class, 1L, properties);
            System.out.println(user.getUserChats());
            System.out.println(user.getCompany());

            var users = session.createQuery(
                    "select u from User u", User.class)
//                    .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCompanyAndUserChat"))
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), userGraph)
                    .list();
            users.forEach(it -> System.out.println(it.getUserChats().size()));
            System.out.println("");
            users.forEach(it -> System.out.println(it.getCompany().getName()));



            session.getTransaction().commit();
        }
    }
}
