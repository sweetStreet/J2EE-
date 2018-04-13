package cn.yuki.dao.daoImpl;

import cn.yuki.dao.UserFinanceDAO;
import cn.yuki.entity.User;
import cn.yuki.entity.UserFinance;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserFinanceDAOImpl implements UserFinanceDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate(UserFinance userFinance) {
        sessionFactory.getCurrentSession().saveOrUpdate(userFinance);
    }

    @Override
    public List find() {
        return sessionFactory.getCurrentSession().createQuery("select new cn.yuki.vo.UserFinanceVO(u.email,-sum(uf.money)) " +
                "from UserFinance uf, User u where uf.userId = u.id group by uf.userId").list();
    }

    @Override
    public List userFind(int userId, int state) {
        return sessionFactory.getCurrentSession().createQuery("from UserFinance as u where u.userId = "+userId+" and state = "+state).list();
    }

    @Override
    public double userFindTotal(int userId, int state) {
        Object result = sessionFactory.getCurrentSession().createQuery(
                "select sum(u.money) from UserFinance as u where u.userId = "+userId+" and u.state = "+state).getSingleResult();
        if(result == null){
            return 0;
        }else{
            return (Double)result;
        }

    }


}
