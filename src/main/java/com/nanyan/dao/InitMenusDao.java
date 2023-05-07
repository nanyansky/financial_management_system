package com.nanyan.dao;

import com.nanyan.entity.Menus.MenuEntity;
import com.nanyan.entity.User;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class InitMenusDao {

    @Autowired
    SessionFactory sessionFactory;

    public List<MenuEntity> findAllByStatusOrderBySort(Boolean status){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        int isAdmin = user.getIsAdmin();

        Session currentSession = sessionFactory.getCurrentSession();
        if(isAdmin != 1){
            Query status1 = currentSession.createQuery("from MenuEntity where status = :status and isAdmin != 1 order by sort asc").setParameter("status", status);
            return status1.list();
        }
        else {
            Query status1 = currentSession.createQuery("from MenuEntity where status = :status order by sort asc").setParameter("status", status);
            return status1.list();
        }
    }
}
