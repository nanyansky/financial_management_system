package com.nanyan.dao;

import com.nanyan.entity.chart.ExpenseCountChart;
import com.nanyan.entity.chart.IncomeCountChart;
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

    public List<IncomeCountChart> getIncomeCount(){
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder hql = new StringBuilder(
                "select COUNT(*) AS count,sum(income_amount) as money, DATE_FORMAT( income_time, '%Y-%m-%d') as date\n" +
                "from income\n" +
                "where DATE_FORMAT( income_time, '%Y-%m-%d') in (");
        List<String> date = GetSevenDate.getDate();
        for (int i = 0; i < date.size()-1; i++) {
            hql.append("'" + date.get(i) + "',");
        }
        hql.append("'" + date.get(date.size()-1) + "') \n" +
                "group by  DATE_FORMAT( income_time, '%Y-%m-%d')\n" +
                "order by  DATE_FORMAT( income_time, '%Y-%m-%d') asc");

        Query query = currentSession.createNativeQuery(String.valueOf(hql)).addScalar("count", IntegerType.INSTANCE).addScalar("money", DoubleType.INSTANCE).addScalar("date", StringType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(IncomeCountChart.class));
        return query.list();
    }


    public List<ExpenseCountChart> getExpenseCount(){
        Session currentSession = sessionFactory.getCurrentSession();
        StringBuilder hql = new StringBuilder(
                "select COUNT(*) AS count,sum(expense_amount) as money, DATE_FORMAT( expense_time, '%Y-%m-%d') as date\n" +
                "from expense\n" +
                "where DATE_FORMAT( expense_time, '%Y-%m-%d') in (");
        List<String> date = GetSevenDate.getDate();
        for (int i = 0; i < date.size()-1; i++) {
            hql.append("'" + date.get(i) + "',");
        }
        hql.append("'" + date.get(date.size()-1) + "') \n" +
                "group by  DATE_FORMAT( expense_time, '%Y-%m-%d')\n" +
                "order by  DATE_FORMAT( expense_time, '%Y-%m-%d') asc");

        Query query = currentSession.createNativeQuery(String.valueOf(hql)).addScalar("count", IntegerType.INSTANCE).addScalar("money", DoubleType.INSTANCE).addScalar("date", StringType.INSTANCE);
        query.setResultTransformer(Transformers.aliasToBean(ExpenseCountChart.class));
        return query.list();
    }
}
