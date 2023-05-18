package com.nanyan.dao;

import com.nanyan.entity.User;
import com.nanyan.entity.UserDebt;
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
public class UserDebtDao {

    @Autowired
    SessionFactory sessionFactory;

    /**
     * 获取所有债务列表
     * @param currentPage
     * @param perPageRows
     * @return
     */
    public List<UserDebt> getDebtListByPage(int currentPage, int perPageRows){

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("from UserDebt where isDeleted != 1 order by debtCreateTime desc")
                .setFirstResult((currentPage - 1) * perPageRows)
                .setMaxResults(perPageRows);

        return query.list();
    }

    /**
     * 获取单个用户债务数量
     * @param currentPage
     * @param perPageRows
     * @return
     */
    public List<UserDebt> getUserDebtListByPage(int currentPage, int perPageRows){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("from UserDebt where debtOwnerId = :debtOwnerId and isDeleted != 1 order by debtCreateTime desc")
                .setParameter("debtOwnerId",user.getId())
                .setFirstResult((currentPage - 1) * perPageRows)
                .setMaxResults(perPageRows);

        return query.list();
    }

    /**
     * 获取所有债务数量
     * @return
     */
    public int getDebtNumber(){

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("select count(debtId) from UserDebt where isDeleted != 1");

        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }

    /**
     * 获取单个用户债务数量
     * @return
     */
    public int getUserDebtNumber(){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("select count(debtId) from UserDebt where debtOwnerId = :debtOwnerId and isDeleted != 1")
                .setParameter("debtOwnerId",user.getId());

        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }

    /**
     * 添加债务
     * @param userDebt
     */
    public void addDebt(UserDebt userDebt){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(userDebt);
    }

    /**
     * 删除债务
     * @param debtId
     */
    public void updateDeleted(int debtId){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update UserDebt set isDeleted = 1 where debtId = :debtId")
                .setParameter("debtId",debtId);
        query.executeUpdate();
    }


    public void updateStatus(int debtId,int debtStatus){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update UserDebt set debtStatus = :debtStatus where debtId = :debtId")
                .setParameter("debtId",debtId)
                .setParameter("debtStatus",debtStatus);
        query.executeUpdate();
    }

    /**
     * 更新债务
     * @param userDebt
     */
    public void updateDebt(UserDebt userDebt){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update UserDebt set debtName = :debtName, debtType = :debtType, debtPrice = :debtPrice, debtRemark = :debtRemark, debtOwnerId = :debtOwnerId, debtOwnerName = :debtOwnerName, debtStatus = :debtStatus where debtId = :debtId")
                .setParameter("debtName",userDebt.getDebtName())
                .setParameter("debtType",userDebt.getDebtType())
                .setParameter("debtPrice",userDebt.getDebtPrice())
                .setParameter("debtRemark",userDebt.getDebtRemark())
                .setParameter("debtId",userDebt.getDebtId())
                .setParameter("debtOwnerId",userDebt.getDebtOwnerId())
                .setParameter("debtOwnerName",userDebt.getDebtOwnerName())
                .setParameter("debtStatus",userDebt.getDebtStatus());
        query.executeUpdate();
    }

    /**
     * 搜索债务
     * @param debtType
     * @return
     */
    public List<UserDebt> searchDebtList(int debtType,int currentPage, int perPageRows) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from UserDebt where debtType = :debtType and isDeleted != 1")
                .setParameter("debtType", debtType)
                .setFirstResult((currentPage - 1) * perPageRows)
                .setMaxResults(perPageRows);
        return query.list();
    }

    public List<UserDebt> searchUserDebtList(int debtType,int currentPage, int perPageRows) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from UserDebt where debtType = :debtType and debtOwnerId = :debtOwnerId and isDeleted != 1")
                .setParameter("debtType", debtType)
                .setParameter("debtOwnerId", user.getId())
                .setFirstResult((currentPage - 1) * perPageRows)
                .setMaxResults(perPageRows);
        return query.list();
    }

    /**
     * 获取搜索债务数量
     * @return
     */
    public int getSearchDebtNumber(int debtType){

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("select count(debtId) from UserDebt where debtType = :debtType and isDeleted != 1");

        query.setParameter("debtType",debtType);
        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }

    /**
     * 获取单个用户债务数量
     * @return
     */
    public int getSearchUserDebtNumber(int debtType){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("select count(debtId) from UserDebt where debtOwnerId = :debtOwnerId and debtType = :debtType and isDeleted != 1")
                .setParameter("debtOwnerId",user.getId())
                .setParameter("debtType",debtType);

        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }

}
