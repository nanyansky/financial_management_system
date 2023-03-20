package com.nanyan.dao;

import com.nanyan.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Slf4j
public class UserDao{

    @Autowired
    SessionFactory sessionFactory;

    public User getUser(int id){
        return sessionFactory.openSession().get(User.class,id);
    }

    public void save(User user) {
        System.out.println(user);
        sessionFactory.openSession().save(user);
    }

    public User findByUserName(String username){
        Session session = sessionFactory.openSession();
        String hql = "from User where userName = :username";
        User user = (User) session.createQuery(hql).setParameter("username", username).uniqueResult();
        System.out.println("UserDao: "+user);
        log.info("UserDao"+user);
        return user;
    }

//
//    //根据主键查询
//    public void getUser(){
//        //获取session
//        Session session = HibernateUtils.getSession();
//        //开启事务
////        Transaction transaction = session.beginTransaction();
//
//        //get方法，主动加载，不用时也会查询，主键不存在返回null
//        User user = session.get(User.class, 1);
//        System.out.println(user);
//        HibernateUtils.closeSession(session);
//
//        //load方法，延迟加载，用的时候才会查，性能稍好，主键不存在会报错
//        User load = session.load(User.class, 1);
//    }
//
//    //HQL语句查询所有用户
//    public List<User> userList(){
//        Session session = HibernateUtils.getSession();
//
//        //自定义HQL语句
//        String hql = "from User";
//        Query query = session.createQuery(hql);
//        //执行查询
//        List<User> list = query.list();
//        //输出
//        for (User u:list) {
//            System.out.println(u);
//        }
//        return list;
//    }
}
