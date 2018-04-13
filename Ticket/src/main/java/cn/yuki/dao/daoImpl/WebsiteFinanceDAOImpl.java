package cn.yuki.dao.daoImpl;

import cn.yuki.dao.WebsiteFinanceDAO;
import cn.yuki.entity.WebsiteFinance;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WebsiteFinanceDAOImpl implements WebsiteFinanceDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate(WebsiteFinance websiteFinance) {
        sessionFactory.getCurrentSession().saveOrUpdate(websiteFinance);
    }

    @Override
    public List findGroupByPlanId() {
        return sessionFactory.getCurrentSession().createQuery("select new cn.yuki.entity.WebsiteFinance(wf.planId,sum(wf.money)) " +
                "from WebsiteFinance wf group by wf.planId").list();
    }

    @Override
    public List findGroupByHouseId() {
        return sessionFactory.getCurrentSession().createQuery("select new cn.yuki.entity.WebsiteFinance(wf.houseId,sum(wf.money)) " +
                "from WebsiteFinance wf group by wf.houseId").list();
    }


}
