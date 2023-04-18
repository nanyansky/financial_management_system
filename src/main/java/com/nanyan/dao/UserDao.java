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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserDao{

    @Autowired
    SessionFactory sessionFactory;


    public User findByUserName(String username){
        Session session = sessionFactory.getCurrentSession();
        String hql = "from User where userName = :username and isDeleted != 1";
        User user = (User) session.createQuery(hql).setParameter("username", username).uniqueResult();
        return user;
    }

    public int getUserNumber(){
        Session session = sessionFactory.getCurrentSession();
        Number ll = (Number) session.createQuery("select count(*) from User where isDeleted != 1").uniqueResult();
        return ll.intValue();
    }

    public List<User> getUserList(){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where isDeleted != 1 and status = 1");
        return query.list();
    }

    public List<User> getUserListByPage(int currentPage,int perPageRows){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where isDeleted != 1");
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }


    /**
     * @description: 根据用户名模糊查询用户列表
     * @param: username
     * @return: java.util.List<com.nanyan.entity.User>
     * @author nanyan
     * @date:  22:15
     */
    public List<User> getUserListByUserName(String username,int currentPage,int perPageRows){
        Session session = sessionFactory.getCurrentSession();
        String s = "%"+username+"%";
        Query query = session.createQuery("from User where userName like :query and isDeleted != 1").setParameter("query",s);
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }

    /** 
     * @description: 根据条件动态搜索 
     * @param: username
isAdmin
status
currentPage
perPageRows 
     * @return: java.util.List<com.nanyan.entity.User> 
     * @author nanyan
     * @date:  16:34
     */ 
    public List<User> searchUser(String username,int isAdmin,int status,int currentPage,int perPageRows){

        Session currentSession = sessionFactory.getCurrentSession();

        Map<String, Object> hqlQueryMap = new HashMap<>();
        hqlQueryMap.put("username",username);
        hqlQueryMap.put("isAdmin",isAdmin);
        hqlQueryMap.put("status",status);
        hqlQueryMap.put("s","%"+username+"%");

        StringBuilder hql = new StringBuilder();
        hql.append("from User where isDeleted != 1 ");

        if(hqlQueryMap.get("username") != ""){
//            System.out.println("username: " + hqlQueryMap.get("username"));
            hql.append(" and userName like :s");
        }
        if ((int)hqlQueryMap.get("isAdmin") != -1){
//            System.out.println("isAdmin: " + hqlQueryMap.get("isAdmin"));
            hql.append(" and isAdmin =:isAdmin");
        }
        if ((int)hqlQueryMap.get("status") != -1){
//            System.out.println("status: " + hqlQueryMap.get("username"));
            hql.append(" and status = :status");
        }

        Query query = currentSession.createQuery(hql.toString());
        System.out.println(hql.toString());
        query.setProperties(hqlQueryMap);
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }


    public int searchUserNumber(String username,int isAdmin,int status){

        Session currentSession = sessionFactory.getCurrentSession();

        Map<String, Object> hqlQueryMap = new HashMap<>();
        hqlQueryMap.put("username",username);
        hqlQueryMap.put("isAdmin",isAdmin);
        hqlQueryMap.put("status",status);
        hqlQueryMap.put("s","%"+username+"%");

        StringBuilder hql = new StringBuilder();
        hql.append("select count(*) from User where isDeleted != 1 ");

        if(hqlQueryMap.get("username") != ""){
//            System.out.println("username: " + hqlQueryMap.get("username"));
            hql.append(" and username like :s");
        }
        if ((int)hqlQueryMap.get("isAdmin") != -1){
//            System.out.println("isAdmin: " + hqlQueryMap.get("isAdmin"));
            hql.append(" and isAdmin =:isAdmin");
        }
        if ((int)hqlQueryMap.get("status") != -1){
//            System.out.println("status: " + hqlQueryMap.get("username"));
            hql.append(" and status = :status");
        }

        Query query = currentSession.createQuery(hql.toString());
        System.out.println(hql.toString());
        query.setProperties(hqlQueryMap);
        Number number = (Number) query.uniqueResult();
        return number.intValue();
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
    public User getUserById(int id){
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.createQuery("from User where id = :id and isDeleted != 1").setParameter("id", id).uniqueResult();
        return user;
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
        String hql = "update User set userName=:username,password=:password,email=:email,isAdmin=:isAdmin,isDeleted=:isDelete,sex=:sex,phoneNumber=:phoneNumber where id="+id;
        Query query = session.createQuery(hql);
//        query.setParameter("id",id);
        query.setParameter("sex",user.getSex());
        query.setParameter("username",user.getUserName());
        query.setParameter("password",user.getPassword());
        query.setParameter("email",user.getEmail());
        query.setParameter("isAdmin",user.getIsAdmin());
        query.setParameter("isDelete",user.getIsDeleted());
        query.setParameter("phoneNumber",user.getPhoneNumber());

        query.executeUpdate();
    }


    /**
     * @description: 用户自己修改信息
     * @param: tmpUsername
user
     * @return: void
     * @author nanyan
     * @date:  13:23
     */
    public void changeInfoByUsername(String tmpUsername,User user){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update User set userName = :username,email=:email, phoneNumber = :phonuseNumber, sex =:sex where userName =:tmpUsername and isDeleted != 1");
        query.setParameter("username",user.getUserName());
        query.setParameter("email",user.getEmail());
        query.setParameter("phonuseNumber",user.getPhoneNumber());
        query.setParameter("sex",user.getSex());
        query.setParameter("tmpUsername",tmpUsername);

        query.executeUpdate();
    }

    /**
     * @description: 用户自己修改密码
     * @param: username
newPwd
     * @return: void
     * @author nanyan
     * @date:  13:24
     */
    public void changePwdByUsername(String username,String newPwd){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update User set password = :password where userName = :username and isDeleted != 1");
        query.setParameter("username",username);
        query.setParameter("password",newPwd);
        query.executeUpdate();
    }

    public List<User> getAllAdmin(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from User where isDeleted != 1 and isAdmin = 1");
        return query.list();

    }

}
