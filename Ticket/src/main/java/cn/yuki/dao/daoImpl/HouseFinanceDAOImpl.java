package cn.yuki.dao.daoImpl;

import cn.yuki.dao.HouseFinanceDAO;
import cn.yuki.entity.HouseFinance;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HouseFinanceDAOImpl implements HouseFinanceDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate(HouseFinance houseFinance) {
        sessionFactory.getCurrentSession().saveOrUpdate(houseFinance);
    }

    @Override
    public List findByHouseId(int houseId) {
        return sessionFactory.getCurrentSession().createQuery("select new cn.yuki.vo.PlanFinanceVO(p.name, sum(hf.money)) " +
                "from HouseFinance hf, Plan p where hf.houseId="+houseId+" and p.houseID = hf.houseId and hf.planId=p.id group by hf.planId").list();
    }

    @Override
    public List find() {
        return sessionFactory.getCurrentSession().createQuery("select new cn.yuki.vo.HouseFinanceVO(h.name,sum(hf.money)) " +
                "from HouseFinance hf, House h where h.id = hf.houseId group by h.id").list();
    }

    @Override
    public List getStatistic(int houseId) {
        return sessionFactory.getCurrentSession().createQuery(
                "select new cn.yuki.vo.HouseFinanceRatioVO(h.state, sum(h.money)) from HouseFinance h where h.houseId="+houseId+" group by h.state").list();
    }


}
