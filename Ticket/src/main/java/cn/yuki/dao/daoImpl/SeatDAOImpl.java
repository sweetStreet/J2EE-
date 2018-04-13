package cn.yuki.dao.daoImpl;

import cn.yuki.dao.SeatDAO;
import cn.yuki.entity.Seat;
import cn.yuki.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatDAOImpl implements SeatDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void update(Seat seat){
        sessionFactory.getCurrentSession().saveOrUpdate(seat);
    }

    @Override
    public Seat find(int planid) {
        List result = sessionFactory.getCurrentSession().createQuery("from Seat as s where s.planId = "+planid).list();
        if(result == null || result.size()==0){
            return null;
        }else{
            return (Seat) result.get(0);
        }
    }
}
