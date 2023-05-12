package com.nanyan.dao;

import com.nanyan.annotation.OptLog;
import com.nanyan.entity.User;
import com.nanyan.entity.UserStock;
import com.nanyan.utils.OperationType;
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
public class StockDao {

    @Autowired
    SessionFactory sessionFactory;


    public List<UserStock> getUserStockListByPage(int currentPage, int perPageRows){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();

        Query fromUserStock = currentSession.createQuery("from UserStock where stockNum > 0 and stockUserId = :userId")
                .setParameter("userId",user.getId())
                .setFirstResult((currentPage - 1) * perPageRows)
                .setMaxResults(perPageRows);

        return fromUserStock.list();
    }


    public void buyStock(UserStock userStock){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(userStock);
    }

    public int getUserStockNumber(){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();

        Query fromUserStock = currentSession.createQuery("select count(stockId) from UserStock where stockUserId = :userId and stockNum > 0")
              .setParameter("userId",user.getId());

        Number number = (Number) fromUserStock.uniqueResult();
        return number.intValue();
    }

}
