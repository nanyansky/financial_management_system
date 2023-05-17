package com.nanyan.dao;

import com.nanyan.entity.StockTrade;
import com.nanyan.entity.User;
import com.nanyan.entity.UserStock;
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
public class StockTradeDao {


    @Autowired
    SessionFactory sessionFactory;

    public void save(StockTrade stockTrade){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(stockTrade);
    }

    public List<StockTrade> getTradeListByPage(int currentPage, int perPageRows){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();
        Query fromStockTrade = currentSession.createQuery("from StockTrade where userId = :userId order by tradeTime desc")
                .setParameter("userId",user.getId())
                .setFirstResult((currentPage - 1) * perPageRows)
                .setMaxResults(perPageRows);

        return fromStockTrade.list();
    }

    public int getTradeNumber(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();
        Query fromStockTrade = currentSession.createQuery("select count(id) from StockTrade where userId = :userId")
                .setParameter("userId",user.getId());

        return ((Number)fromStockTrade.uniqueResult()).intValue();
    }

    public List<StockTrade> searchTradList(int currentPage, int perPageRows, int tradeType){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();
        Query fromStockTrade = currentSession.createQuery("from StockTrade where userId = :userId and tradeType = :tradeType order by tradeTime desc")
                .setParameter("userId",user.getId())
                .setParameter("tradeType",tradeType)
                .setFirstResult((currentPage - 1) * perPageRows)
                .setMaxResults(perPageRows);

        return fromStockTrade.list();
    }

    public int getSearchTradeNumber(int tradeType){
        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();
        Query fromStockTrade = currentSession.createQuery("select count(id) from StockTrade where userId = :userId and tradeType = :tradeType order by tradeTime desc")
                .setParameter("userId",user.getId())
                .setParameter("tradeType",tradeType);

        return ((Number)fromStockTrade.uniqueResult()).intValue();
    }

}
