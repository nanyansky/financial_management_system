package com.nanyan.dao;

import com.nanyan.entity.OperationLog;
import com.nanyan.utils.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    public void addOperationLog(OperationLog operationLog){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(operationLog);
    }
}
