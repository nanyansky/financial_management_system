package com.nanyan.dao;

import com.nanyan.entity.chart.ExpenseCountChart;
import com.nanyan.entity.chart.ExpenseTypeChart;
import com.nanyan.entity.chart.IncomeCountChart;
import com.nanyan.entity.chart.IncomeTypeChart;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.nanyan.utils.GetSevenDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/6 20:21
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChartDao {
    @Autowired
    SessionFactory sessionFactory;


    public List<IncomeCountChart> getIncomeCount(String username){
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder hql = new StringBuilder(
                "select COUNT(*) AS count,sum(income_amount) as money, DATE_FORMAT( income_time, '%Y-%m-%d') as date\n" +
                "from income\n" +
                "where income.is_deleted != 1 and DATE_FORMAT( income_time, '%Y-%m-%d') in (");
        List<String> date = GetSevenDate.getDate();
        for (int i = 0; i < date.size()-1; i++) {
            hql.append("'" + date.get(i) + "',");
        }
        hql.append("'" + date.get(date.size()-1) + "') \n");

        if(!Objects.equals(username, "")&& username != null){
            hql.append(" and income.user_name =:username ");
        }
        hql.append(" group by DATE_FORMAT( income_time, '%Y-%m-%d') order by  DATE_FORMAT( income_time, '%Y-%m-%d') asc");

        Query query = currentSession.createNativeQuery(String.valueOf(hql)).addScalar("count", IntegerType.INSTANCE).addScalar("money", DoubleType.INSTANCE).addScalar("date", StringType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(IncomeCountChart.class));
        if(!Objects.equals(username, "")&& username != null){
            query.setParameter("username",username);
        }
        return query.list();
    }


    public List<ExpenseCountChart> getExpenseCount(String username){
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder hql = new StringBuilder(
                "select COUNT(*) AS count,sum(expense_amount) as money, DATE_FORMAT( expense_time, '%Y-%m-%d') as date\n" +
                "from expense\n" +
                "where expense.is_deleted != 1 and DATE_FORMAT( expense_time, '%Y-%m-%d') in (");
        List<String> date = GetSevenDate.getDate();
        for (int i = 0; i < date.size()-1; i++) {
            hql.append("'" + date.get(i) + "',");
        }
        hql.append("'" + date.get(date.size()-1) + "') \n");

        if(!Objects.equals(username, "")&& username != null){
            hql.append(" and expense.user_name =:username ");
        }

        hql.append(" group by DATE_FORMAT( expense_time, '%Y-%m-%d') order by  DATE_FORMAT( expense_time, '%Y-%m-%d') asc");

        Query query = currentSession.createNativeQuery(String.valueOf(hql)).addScalar("count", IntegerType.INSTANCE).addScalar("money", DoubleType.INSTANCE).addScalar("date", StringType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(ExpenseCountChart.class));
        if(!Objects.equals(username, "")&& username != null){
            query.setParameter("username",username);
        }
        return query.list();
    }

    public List<IncomeTypeChart> getIncomeTypeData(String username){
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder(
                "select t.typeName, count(income_type_id) as typeCount,sum(income_amount) as typeMoney\n" +
                "from income,(select name as typeName,id from income_type where is_deleted != 1)t\n" +
                "where income.is_deleted != 1 and income_type_id = t.id");
        if(!Objects.equals(username, "")&& username != null){
            sql.append(" and income.user_name =:username ");
        }
        sql.append(" group by income_type_id");

        Query query = currentSession.createNativeQuery(sql.toString()).addScalar("typeName",StringType.INSTANCE).addScalar("typeCount",IntegerType.INSTANCE).addScalar("typeMoney",DoubleType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(IncomeTypeChart.class));
        if(!Objects.equals(username, "")&& username != null){
            query.setParameter("username",username);
        }
        return query.list();
    }

    public List<ExpenseTypeChart> getExpenseTypeData(String username){
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder(
                "select t.typeName, count(expense_type_id) as typeCount,sum(expense_amount) as typeMoney\n" +
                "from expense,(select name as typeName,id from expense_type where is_deleted != 1)t\n" +
                "where expense.is_deleted != 1 and expense.expense_type_id = t.id ");
        if(!Objects.equals(username, "")&& username != null){
            sql.append(" and expense.user_name =:username ");
        }
        sql.append(" group by expense_type_id");

        Query query = currentSession.createNativeQuery(sql.toString()).addScalar("typeName",StringType.INSTANCE).addScalar("typeCount",IntegerType.INSTANCE).addScalar("typeMoney",DoubleType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(ExpenseTypeChart.class));
        if(!Objects.equals(username, "")&& username != null){
            query.setParameter("username",username);
        }
        return query.list();
    }
}
