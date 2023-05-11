package com.nanyan.dao;

import com.nanyan.entity.UserStock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StockDao {

    @Autowired
    SessionFactory sessionFactory;

    public List<UserStock> getUserStockListByPage(int currentPage, int perPageRows){
        Session currentSession = sessionFactory.getCurrentSession();

        Query fromUserStock = currentSession.createQuery("from UserStock").setFirstResult((currentPage - 1) * perPageRows).setMaxResults(perPageRows);

        return null;

    }
}
