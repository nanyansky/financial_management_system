package com.nanyan.dao;

import com.nanyan.entity.User;
import com.nanyan.entity.UserAssets;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserAssetsDao {

    @Autowired
    SessionFactory sessionFactory;

    /**
     * 获取所有资产列表
     * @param currentPage
     * @param perPageRows
     * @return
     */
    public List<UserAssets> getAssetsListByPage(int currentPage, int perPageRows){

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("from UserAssets where isDeleted != 1 order by assetsCreateTime desc")
                .setFirstResult((currentPage - 1) * perPageRows)
                .setMaxResults(perPageRows);

        return query.list();
    }

    /**
     * 获取单个用户资产数量
     * @param currentPage
     * @param perPageRows
     * @return
     */
    public List<UserAssets> getUserAssetsListByPage(int currentPage, int perPageRows){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("from UserAssets where assetsOwnerId = :assetsOwnerId and isDeleted != 1 order by assetsCreateTime desc")
                .setParameter("assetsOwnerId",user.getId())
                .setFirstResult((currentPage - 1) * perPageRows)
                .setMaxResults(perPageRows);

        return query.list();
    }
    
    /**
     * 获取所有资产数量
     * @return
     */
    public int getAssetsNumber(){

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("select count(assetsId) from UserAssets where isDeleted != 1");

        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }
    
    /**
     * 获取单个用户资产数量
     * @return
     */
    public int getUserAssetsNumber(){

        HttpSession session = ServletActionContext.getRequest().getSession();
        User user = (User) session.getAttribute("user");

        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("select count(assetsId) from UserAssets where assetsOwnerId = :assetsOwnerId and isDeleted != 1")
                .setParameter("assetsOwnerId",user.getId());

        Number number = (Number) query.uniqueResult();
        return number.intValue();
    }

    /**
     * 添加资产
     * @param userAssets
     */
    public void addAssets(UserAssets userAssets){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(userAssets);
    }

    /**
     * 删除资产
     * @param assetsId
     */
    public void updateDeleted(int assetsId){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update UserAssets set isDeleted = 1 where assetsId = :assetsId")
                .setParameter("assetsId",assetsId);
        System.out.println("query:"+query);
        query.executeUpdate();
    }

    /**
     * 更新资产
     * @param userAssets
     */
    public void updateAssets(UserAssets userAssets){
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("update UserAssets set assetsName = :assetsName, assetsLocation = :assetsLocation, assetsPrice = :assetsPrice, assetsRemark = :assetsRemark, assetsOwnerId = :assetsOwnerId, assetsOwnerName = :assetsOwnerName where assetsId = :assetsId")
                .setParameter("assetsName",userAssets.getAssetsName())
                .setParameter("assetsLocation",userAssets.getAssetsLocation())
                .setParameter("assetsPrice",userAssets.getAssetsPrice())
                .setParameter("assetsRemark",userAssets.getAssetsRemark())
                .setParameter("assetsId",userAssets.getAssetsId())
                .setParameter("assetsOwnerId",userAssets.getAssetsOwnerId())
                .setParameter("assetsOwnerName",userAssets.getAssetsOwnerName());
        query.executeUpdate();
    }

}
