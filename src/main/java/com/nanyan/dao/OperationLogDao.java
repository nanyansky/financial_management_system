package com.nanyan.dao;

import com.nanyan.entity.OperationLog;
import com.nanyan.utils.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/1 11:37
 */
@Repository
@Slf4j
@Transactional
public class OperationLogDao {

    @Autowired
    SessionFactory sessionFactory;

    //添加日志
    public void addOperationLog(OperationLog operationLog){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(operationLog);
    }

    //日志数量
    public int getLogNumber(){
        Session currentSession = sessionFactory.getCurrentSession();
        Number logNumber = (Number) currentSession.createQuery("select count(*) from OperationLog").uniqueResult();
        return logNumber.intValue();
    }

    //日志列表
    public List<OperationLog> getLogListByPage(int currentPage,int perPageRows){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from OperationLog");
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }

    public List<OperationLog> getLogList(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from OperationLog");
        return query.list();
    }

    //根据用户名查找日志列表
    public List<OperationLog> getLogListByUserName(String username,int currentPage,int perPageRows){
        Session currentSession = sessionFactory.getCurrentSession();
        String s = "%"+username+"%";
        Query query = currentSession.createQuery("from OperationLog where userName like :username").setParameter("username", s);
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }




}
