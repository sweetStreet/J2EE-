package cn.yuki.dao.daoImpl;

import cn.yuki.dao.OrderDAO;
import cn.yuki.entity.Order;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int save(Order order){
        return (Integer) sessionFactory.getCurrentSession().save(order);
    }

    @Override
    @Transactional
    public void update(Order order) {
        sessionFactory.getCurrentSession().update(order);
    }

    @Override
    @Transactional
    public Order find(int id) {
        return sessionFactory.getCurrentSession().find(Order.class, id);
    }

    @Override
    public List<Order> userFindList(int userId) {
        return sessionFactory.getCurrentSession().createQuery("from Order as o where o.userID ="+userId).list();
    }

    @Override
    public List<Order> userfindListByState(int userId, String state) {
        return sessionFactory.getCurrentSession().createQuery(
                "from Order as o where o.userID = "+userId+" and o.state= "+state).list();
    }

    @Override
    public List<Order> houseFindList(int houseId) {
        return sessionFactory.getCurrentSession().createQuery(
                "select new cn.yuki.entity.Order(o.id, o.planID, o.planName, o.payment, o.createTime, o.state) " +
                        "from Order o, Plan p where o.planID=p.id and p.houseID="+houseId).list();
    }

    @Override
    public List<Order> houseFindListByState(int houseId, String state) {
        return sessionFactory.getCurrentSession().createQuery(
                "select new cn.yuki.entity.Order(o.id, o.planID, o.planName, o.payment, o.createTime, o.state) " +
                        "from Order o, Plan p where o.planID=p.id and p.houseID="+houseId+" and o.state="+state).list();
    }
}
