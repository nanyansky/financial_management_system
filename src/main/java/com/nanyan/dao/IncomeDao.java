package com.nanyan.dao;

import com.nanyan.entity.Income;
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

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 11:49
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IncomeDao {

    @Autowired
    SessionFactory sessionFactory;

    /**
     * @description: 获取收入帐单数
     * @param: null
     * @return: int
     * @author nanyan
     * @date:  13:12
     */
    public int getIncomeNumber(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("select count(*) from Income where isDeleted != 1");
        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }

    /**
     * @description: 获取收入帐单列表
     * @param: null
     * @return: java.util.List<com.nanyan.entity.Income>
     * @author nanyan
     * @date:  13:12
     */
    public List<Income> getIncomeList(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from Income where isDeleted != 1");
        return query.list();
    }

    /** 
     * @description: 获取收入帐单列表(分页) 
     * @param: currentPage
perPageRows 
     * @return: java.util.List<com.nanyan.entity.Income> 
     * @author nanyan
     * @date:  14:57
     */ 
    public List<Income> getIncomeListByPage(int currentPage, int perPageRows){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from Income where isDeleted != 1");
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }

    /**
     * @description:  根据用户名模糊查询收入账单
     * @param: username
    currentPage
    perPageRows
     * @return: java.util.List<com.nanyan.entity.Income>
     * @author nanyan
     * @date:  13:13
     */
    public List<Income> getIncomeListByUserName(String username,int currentPage,int perPageRows){
        Session session = sessionFactory.getCurrentSession();
        String s = "%"+username+"%";
        Query query = session.createQuery("from Income where userName like :query and isDeleted != 1").setParameter("query",s);
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }

    /**
     * @description: 添加收入账单
     * @param: income
     * @return: void
     * @author nanyan
     * @date:  13:15
     */
    public void addIncome(Income income){
        Session currentSession = sessionFactory.getCurrentSession();
        income.setIncomeTime(new Timestamp(System.currentTimeMillis()));
        currentSession.save(income);
    }

    /**
     * @description: 根据id删除收入账单
     * @param: id
     * @return: void
     * @author nanyan
     * @date:  13:17
     */
    public void deleteIncomeById(int id){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update Income set isDeleted = 1 where id =:id").setParameter("id", id);
        query.executeUpdate();

    }

    /**
     * @description: 修改收入账单信息
     * @param: id, income 
     * @return: void
     * @author nanyan
     * @date:  13:24
     */
    public void editIncome(int id, Income income){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update Income set userName =:username,userId =:userId, incomeSource =:incomeSource, incomeAmount =:incomeAmount, isDeleted =:isDeleted where id =:id");
        query.setParameter("id",id);
        query.setParameter("username",income.getUserName());
        query.setParameter("userId",income.getUserId());
        query.setParameter("incomeSource",income.getIncomeSource());
        query.setParameter("incomeAmount",income.getIncomeAmount());
        query.setParameter("isDeleted",income.getIsDeleted());

        query.executeUpdate();
    }

    /**
     * @description: 获取总支出金额
     * @param: null
     * @return: double
     * @author nanyan
     * @date:  17:02
     */
    public double getIncomeCount(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("select sum(incomeAmount) from Income where isDeleted != 1");
        Number number = query.uniqueResult() == null ? 0 : (Number) query.uniqueResult();
        return number.doubleValue();
    }
}
