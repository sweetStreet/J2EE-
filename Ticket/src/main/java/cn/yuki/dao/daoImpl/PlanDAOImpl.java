package cn.yuki.dao.daoImpl;

import cn.yuki.dao.PlanDAO;
import cn.yuki.entity.Plan;
import cn.yuki.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanDAOImpl implements PlanDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public int save(Plan plan) {
        return (Integer) sessionFactory.getCurrentSession().save(plan);
    }

    public List findAll() {
        return (List) sessionFactory.getCurrentSession().createQuery("from Plan as p where p.startTime > now()").list();
    }

    public List findByType(String type){
        List result = sessionFactory.getCurrentSession().createQuery("from Plan as p where p.type = '"+type+"' and p.startTime > now()").list();
        return result;
    }

    @Override
    public Plan find(String id) {
        List result = sessionFactory.getCurrentSession().createQuery("from Plan as p where p.id = "+id).list();
        if(result == null || result.size()==0){
            return null;
        }else{
            return (Plan) result.get(0);
        }
    }

    @Override
    public List findByHouse(int houseId) {
        return sessionFactory.getCurrentSession().createQuery("from Plan as p where p.houseID="+houseId).list();
    }

    @Override
    public List findFinishedList() {
        return sessionFactory.getCurrentSession().createQuery(
                "select new cn.yuki.vo.PlanVO(p.id,p.name, p.startTime, p.endTime,p.houseID, h.name, p.checkMoney,sum(hf.money))" +
                        "from Plan p, HouseFinance hf, House h where now()>p.endTime and p.id=hf.planId and p.houseID=h.id group by p.id").list();
    }

    @Override
    public void update(Plan plan) {
        sessionFactory.getCurrentSession().update(plan);
    }
}
