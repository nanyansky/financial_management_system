package com.nanyan.dao;

import com.nanyan.annotation.OptLog;
import com.nanyan.entity.Expense;
import com.nanyan.entity.Income;
import com.nanyan.utils.OperationType;
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

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 11:49
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExpenseDao {

    @Autowired
    SessionFactory sessionFactory;

    /**
     * @description: 获取支出帐单数
     * @param: null
     * @return: int
     * @author nanyan
     * @date:  13:12
     */
    public int getExpenseNumber(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("select count(*) from Expense where isDeleted != 1");
        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }

    /**
     * @description: 获取支出帐单列表
     * @param: null
     * @return: java.util.List<com.nanyan.entity.Expense>
     * @author nanyan
     * @date:  13:12
     */
    public List<Expense> getExpenseList(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from Expense where isDeleted != 1 order by expenseTime desc");
        return query.list();
    }

    /**
     * @description: 获取支出帐单列表(分页)
     * @param: currentPage perPageRows
     * @return: java.util.List<com.nanyan.entity.Expense>
     * @author nanyan
     * @date:  13:12
     */
    public List<Expense> getExpenseListByPage(int currentPage,int perPageRows){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from Expense where isDeleted != 1 order by expenseTime desc");
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }

    /**
     * @description:  根据用户名模糊查询支出账单
     * @param: username
               currentPage
               perPageRows
     * @return: java.util.List<com.nanyan.entity.Expense>
     * @author nanyan
     * @date:  13:13
     */
    public List<Expense> getExpenseListByUserName(String username,int currentPage,int perPageRows){
        Session session = sessionFactory.getCurrentSession();
        String s = "%"+username+"%";
        Query query = session.createQuery("from Expense where userName like :query and isDeleted != 1 order by expenseTime desc").setParameter("query",s);
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }

    public List<Expense> searchExpense(String username,String userNameAcc, int expenseTypeId, Timestamp startTime, Timestamp endTime, int currentPage, int perPageRows){


        Session currentSession = sessionFactory.getCurrentSession();

        Map<String, Object> hqlQueryMap = new HashMap<>();
        hqlQueryMap.put("username",username);
        hqlQueryMap.put("userNameAcc",userNameAcc);
        hqlQueryMap.put("expenseTypeId",expenseTypeId);
        hqlQueryMap.put("startTime",startTime);
        hqlQueryMap.put("endTime",endTime);
        hqlQueryMap.put("s","%"+username+"%");

        System.out.println(hqlQueryMap);

        StringBuilder hql = new StringBuilder();
        hql.append("from Expense where isDeleted != 1 ");

        if(hqlQueryMap.get("username") != "" && hqlQueryMap.get("username") != null){
//            System.out.println("username: " + hqlQueryMap.get("username"));
            hql.append(" and userName like :s");
        }
        if(hqlQueryMap.get("userNameAcc") != "" && hqlQueryMap.get("userNameAcc") != null){
//            System.out.println("username: " + hqlQueryMap.get("username"));
            hql.append(" and userName =:userNameAcc");
        }
        if ((int)hqlQueryMap.get("expenseTypeId") != -1 && (int)hqlQueryMap.get("expenseTypeId") != 0){
//            System.out.println("isAdmin: " + hqlQueryMap.get("isAdmin"));
            hql.append(" and expenseTypeId =:expenseTypeId");
        }
        if (hqlQueryMap.get("startTime") != null){
            System.out.println("startTime: " + hqlQueryMap.get("startTime"));
            hql.append(" and expenseTime between :startTime and :endTime");
        }

        hql.append(" order by expenseTime desc");
        Query query = currentSession.createQuery(hql.toString());
//        System.out.println(hql.toString());
        query.setProperties(hqlQueryMap);
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }


    public int searchExpenseNumber(String username,String userNameAcc, int expenseTypeId, Timestamp startTime, Timestamp endTime){

        Session currentSession = sessionFactory.getCurrentSession();

        Map<String, Object> hqlQueryMap = new HashMap<>();
        hqlQueryMap.put("username",username);
        hqlQueryMap.put("userNameAcc",userNameAcc);
        hqlQueryMap.put("expenseTypeId",expenseTypeId);
        hqlQueryMap.put("startTime",startTime);
        hqlQueryMap.put("endTime",endTime);
        hqlQueryMap.put("s","%"+username+"%");

        StringBuilder hql = new StringBuilder();
        hql.append("select count(*) from Expense where isDeleted != 1 ");

        if(hqlQueryMap.get("username") != "" && hqlQueryMap.get("username") != null){
//            System.out.println("username: " + hqlQueryMap.get("username"));
            hql.append(" and userName like :s");
        }
        if(hqlQueryMap.get("userNameAcc") != "" && hqlQueryMap.get("userNameAcc") != null){
//            System.out.println("username: " + hqlQueryMap.get("username"));
            hql.append(" and userName =:userNameAcc");
        }
        if ((int)hqlQueryMap.get("expenseTypeId") != -1 && (int)hqlQueryMap.get("expenseTypeId") != 0){
//            System.out.println("isAdmin: " + hqlQueryMap.get("isAdmin"));
            hql.append(" and expenseTypeId =:expenseTypeId");
        }
        if (hqlQueryMap.get("startTime") != null){
            System.out.println("startTime: " + hqlQueryMap.get("startTime"));
            hql.append(" and expenseTime between :startTime and :endTime");
        }

        Query query = currentSession.createQuery(hql.toString());
//        System.out.println(hql.toString());
        query.setProperties(hqlQueryMap);
        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }
    
    /**
     * @description: 添加支出账单
     * @param: expense
     * @return: void
     * @author nanyan
     * @date:  13:15
     */
    public void addExpense(Expense expense){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(expense);
    }

    /**
     * @description: 根据id删除用户
     * @param: id
     * @return: void
     * @author nanyan
     * @date:  13:17
     */
    public void deleteExpenseById(int id){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update Expense set isDeleted = 1 where id =:id").setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * @description: 修改账单信息
     * @param: id, expense
     * @return: void
     * @author nanyan
     * @date:  13:24
     */
    @OptLog(content = "编辑支出记录", operationType = OperationType.UPDATE)
    public void editExpense(int id, Expense expense){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update Expense set userName =:username,userId =:userId, expenseTypeId =:expenseTypeId,expenseTime=:expenseTime,expenseContent=:expenseContent, expenseAmount =:expenseAmount, isDeleted =:isDeleted where id =:id");
        query.setParameter("id",id);
        query.setParameter("username",expense.getUserName());
        query.setParameter("userId",expense.getUserId());
        query.setParameter("expenseTypeId",expense.getExpenseTypeId());
        query.setParameter("expenseTime",expense.getExpenseTime());
        query.setParameter("expenseContent",expense.getExpenseContent());
        query.setParameter("expenseAmount",expense.getExpenseAmount());
        query.setParameter("isDeleted",expense.getIsDeleted());

        query.executeUpdate();
    }

    /**
     * @description: 获取支出总金额
     * @param: null
     * @return: double
     * @author nanyan
     * @date:  17:04
     */
    public double getExpenseCount(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("select sum(expenseAmount) from Expense where isDeleted != 1");
        Number number = query.uniqueResult() == null ? 0 : (Number) query.uniqueResult();
        return number.doubleValue();
    }

}
