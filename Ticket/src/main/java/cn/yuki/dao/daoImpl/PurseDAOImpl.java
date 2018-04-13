package cn.yuki.dao.daoImpl;

import cn.yuki.dao.PurseDAO;
import cn.yuki.entity.Purse;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurseDAOImpl implements PurseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Purse findByUserId(int userId) {
        List result = sessionFactory.getCurrentSession().createQuery("from Purse as p where p.userId = "+userId).list();
        if(result == null || result.size()==0){
            return null;
        }else{
            return (Purse) result.get(0);
        }
    }

    @Override
    public void update(Purse purse) {
       sessionFactory.getCurrentSession().update(purse);
    }


}
