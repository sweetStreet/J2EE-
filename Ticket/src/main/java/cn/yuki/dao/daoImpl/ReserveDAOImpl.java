package cn.yuki.dao.daoImpl;

import cn.yuki.dao.ReserveDAO;
import cn.yuki.entity.Reserve;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ReserveDAOImpl implements ReserveDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate(Reserve reserve) {
        sessionFactory.getCurrentSession().saveOrUpdate(reserve);
    }

    @Override
    @Transactional
    public List findTwoWeekLater() {
        List list = sessionFactory.getCurrentSession().createQuery("from Reserve as r where datediff(r.kplanTime,now())=14").list();
        if(list == null || list.size()==0) {
            return null;
        }else{
            return list;
        }
    }

    @Override
    public List findReserves(int houseId) {
        return sessionFactory.getCurrentSession().createQuery("select new cn.yuki.entity.HouseFinance(h.houseId,sum(h.money)) " +
                "from HouseFinance h group by h.houseId").list();
    }


}
