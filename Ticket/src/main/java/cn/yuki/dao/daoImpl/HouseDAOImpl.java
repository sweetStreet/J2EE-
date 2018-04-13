package cn.yuki.dao.daoImpl;

import cn.yuki.dao.HouseDAO;
import cn.yuki.entity.House;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HouseDAOImpl implements HouseDAO {

    @Autowired
    SessionFactory sessionFactory;

    public int save(House house) {
        return (Integer) sessionFactory.getCurrentSession().save(house);
    }

    public House find(String id) {
        List result = sessionFactory.getCurrentSession().createQuery("from House as h where h.id = '"+id+"'").list();
        if(result == null || result.size()==0){
            return null;
        }else{
            return (House) result.get(0);
        }
    }

    @Override
    public List findByState(String state) {
        return sessionFactory.getCurrentSession().createQuery("from House as h where h.isValid = "+state).list();
    }

    @Override
    public List findAll() {
        return sessionFactory.getCurrentSession().createQuery("from House").list();
    }

    public void update(House house) {
        System.out.println("update house here!");
        System.out.println(house.getPassword());
        sessionFactory.getCurrentSession().update(house);
    }
}
