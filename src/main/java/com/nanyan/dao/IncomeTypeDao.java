package com.nanyan.dao;

import com.nanyan.entity.IncomeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/2 18:57
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IncomeTypeDao {

    @Autowired
    SessionFactory sessionFactory;

    /**
     * @description: 获取分类数量
     * @param: null
     * @return: int
     * @author nanyan
     * @date:  19:05
     */
    public int getIncomeTypeNumber(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("select count(*) from IncomeType where isDeleted != 1");
        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }

    /**
     * @description:  获取分类列表
     * @param: null
     * @return:  List
     * @author nanyan
     * @date:  19:05
     */
    public List<IncomeType> getIncomeTypeList(){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from IncomeType where isDeleted != 1");
        return query.list();
    }

    /**
     * @description: 获取分类列表(分页)
     * @param: currentPage
perPageRows
     * @return: java.util.List<com.nanyan.entity.IncomeType>
     * @author nanyan
     * @date:  14:07
     */
    public List<IncomeType> getIncomeTypeListByPage(int currentPage,int perPageRows){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from IncomeType where isDeleted != 1");
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }

    /**
     * @description: 根据分类名称模糊搜索
     * @param: name
currentPage
perPageRows
     * @return: java.util.List<com.nanyan.entity.IncomeType>
     * @author nanyan
     * @date:  19:06
     */
    public List<IncomeType> getIncomeTypeListByName(String name,int currentPage,int perPageRows) {
        Session session = sessionFactory.getCurrentSession();
        String s = "%"+name+"%";
        Query query = session.createQuery("from IncomeType where name like :query and isDeleted != 1").setParameter("query",s);
        query.setFirstResult(perPageRows*(currentPage-1)).setMaxResults(perPageRows);
        return query.list();
    }

    /**
     * @description: 添加分类
     * @param: expenseType
     * @return: void
     * @author nanyan
     * @date:  19:07
     */
    public void addIncomeType(IncomeType expenseType){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(expenseType);
    }

    /**
     * @description: 删除分类
     * @param: id
     * @return: void
     * @author nanyan
     * @date:  19:07
     */
    public void deleteIncomeByTypeById(int id){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update IncomeType set isDeleted = 1 where id =:id").setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * @description: 编辑分类名称
     * @param: id
expenseType
     * @return: void
     * @author nanyan
     * @date:  19:11
     */
    public void editIncomeType(int id, IncomeType expenseType){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update IncomeType set name =:name, createTime =:createTime where id =:id");
        query.setParameter("id",id);
        query.setParameter("name",expenseType.getName());
        query.setParameter("createTime",expenseType.getCreateTime());

        query.executeUpdate();
    }

    /**
     * @description: 根据支出类别id查找支出类别名称
     * @param: id
     * @return: java.lang.String
     * @author nanyan
     * @date:  11:21
     */
    public String getIncomeTypeNameById(int id){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("select name from IncomeType  where id =:id").setParameter("id", id);
        String expenseTypeName = (String) query.uniqueResult();
        System.out.println(expenseTypeName);
        return expenseTypeName;
    }

    /**
     * @description: 根据分类名称查找分类
     * @param: name
     * @return: com.nanyan.entity.IncomeType
     * @author nanyan
     * @date:  11:44
     */
    public IncomeType getIncomeTypeByName(String name){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from IncomeType where name =:name and isDeleted != 1").setParameter("name", name);
        IncomeType expenseType = (IncomeType) query.uniqueResult();
        return expenseType;
    }

}
