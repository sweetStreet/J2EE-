package cn.yuki.dao.daoImpl;

import cn.yuki.dao.UserDAO;
import cn.yuki.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(User u) {
        return (Integer) sessionFactory.getCurrentSession().save(u);

    }

    public User find(String email) {
        List result = sessionFactory.getCurrentSession().createQuery("from User as u where u.email = '"+email+"'").list();
        if(result == null || result.size()==0){
            return null;
        }else{
            return (User) result.get(0);
        }
    }

    @Override
    public User find(int userId) {
        return sessionFactory.getCurrentSession().find(User.class, userId);
    }

    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }



}