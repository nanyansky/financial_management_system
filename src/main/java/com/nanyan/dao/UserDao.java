package com.nanyan.dao;

import com.nanyan.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserDao{

    @Autowired
    SessionFactory sessionFactory;


    public User findByUserName(String username){
        Session session = sessionFactory.getCurrentSession();
        String hql = "from User where userName = :username and isDeleted != 1";
        User user = (User) session.createQuery(hql).setParameter("username", username).uniqueResult();
        System.out.println("UserDao: "+user);
        log.info("UserDao"+user);
        return user;
    }

    public List<User> getUserListByPage(int currentPage,int perPageRows){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where isDeleted != 1");
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }

    public int getUserNumber(){
        Session session = sessionFactory.getCurrentSession();
        Number ll = (Number) session.createQuery("select count(*) from User where isDeleted != 1").uniqueResult();
        return ll.intValue();
    }


    /**
     * @description: 添加用户
     * @param: null
     * @return: null
     * @author nanyan
     * @date:  20:11
     */
    public void addUser(User user){
        Session session = sessionFactory.getCurrentSession();
        user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        session.save(user);
    }

    /**
     * @description: 根据用户名查找用户
     * @param: String
     * @return: User
     * @author nanyan
     * @date:  20:13
     */
    public User findByUserId(int id){
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.createQuery("from User where id = :id and isDeleted != 1").setParameter("id", id).uniqueResult();
        return user;
    }

    /**
     * @description: 根据用户名模糊查询用户列表
     * @param: username
     * @return: java.util.List<com.nanyan.entity.User>
     * @author nanyan
     * @date:  22:15
     */
    public List<User> findListByUserName(String username){
        Session session = sessionFactory.getCurrentSession();
        String s = "%"+username+"%";
        Query query = session.createQuery("from User where userName like :query and isDeleted != 1").setParameter("query",s);
        System.out.println(query.list());
        return query.list();
    }


    public void changeUserStatus(int id,int status){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update User set status = :status where id = :id");
        query.setParameter("status",status);
        query.setParameter("id",id);
        query.executeUpdate();
    }

    /**
     * @description: 根据id删除用户
     * @param: User
     * @return:  null
     * @author nanyan
     * @date:  20:17
     */
    public void deleteById(int id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("update User set isDeleted = 1 where id =:id").setParameter("id",id).executeUpdate();
    }

    /**
     * @description: 修改用户
     * @param: User
     * @return: null
     * @author nanyan
     * @date:  20:21
     */
    public void editUser(int id,User user){
        System.out.println("获取到的id："+id);
        Session session = sessionFactory.getCurrentSession();
//        User originalUser = findByUserId(id);
        String hql = "update User set userName=:username,password=:password,isAdmin=:isAdmin,isDeleted=:isDelete,sex=:sex,phoneNumber=:phoneNumber where id="+id;
        Query query = session.createQuery(hql);
//        query.setParameter("id",id);
        query.setParameter("sex",user.getSex());
        query.setParameter("username",user.getUserName());
        query.setParameter("password",user.getPassword());
        query.setParameter("isAdmin",user.getIsAdmin());
        query.setParameter("isDelete",user.getIsDeleted());
        query.setParameter("phoneNumber",user.getPhoneNumber());

        query.executeUpdate();
    }


    public void changeInfoByUsername(String tmpUsername,User user){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update User set userName = :username, phoneNumber = :phonuseNumber, sex =:sex where userName =:tmpUsername and isDeleted != 1");
        query.setParameter("username",user.getUserName());
        query.setParameter("phonuseNumber",user.getPhoneNumber());
        query.setParameter("sex",user.getSex());
        query.setParameter("tmpUsername",tmpUsername);

        query.executeUpdate();
    }

    public void changePwdByUsername(String username,String newPwd){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update User set password = :password where userName = :username and isDeleted != 1");
        query.setParameter("username",username);
        query.setParameter("password",newPwd);
        query.executeUpdate();
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
