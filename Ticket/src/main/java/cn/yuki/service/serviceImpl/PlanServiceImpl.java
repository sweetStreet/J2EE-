package cn.yuki.service.serviceImpl;

import cn.yuki.dao.HouseDAO;
import cn.yuki.dao.OrderDAO;
import cn.yuki.dao.PlanDAO;
import cn.yuki.entity.Plan;
import cn.yuki.service.PlanService;
import cn.yuki.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("planService")
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanDAO planDAO;

    @Autowired
    HouseDAO houseDAO;

    @Autowired
    OrderDAO orderDAO;

    @Transactional
    public Result makePlan(String name, String startTime, String endTime, String type, String description, String price, String fileLocation, String houseID, String seatmap) {
        Plan plan = new Plan();
        plan.setName(name);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            plan.setStartTime(formatter.parse(startTime));
            plan.setEndTime(formatter.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        plan.setType(type);
        plan.setDescription(description);
        plan.setPrice(price);
        plan.setFileLocation(fileLocation);
        plan.setHouseID(houseID);
        plan.setSeatmap(seatmap);

        System.out.println("开始时间"+startTime);
        System.out.println("结束时间"+endTime);
        System.out.println(planDAO.save(plan));
        return Result.Success;
    }

    public List getPlan(String type) {
        //这里的plan都是演出日期在当前时间之后的
        if(type == null) {
            return planDAO.findAll();
        }else{
            return planDAO.findByType(type);
        }
    }

    @Override
    public Plan getPlanDetail(String id) {
        return planDAO.find(id);
    }

    @Override
    public List getPlanByHouse(int houseId) {
        return planDAO.findByHouse(houseId);
    }

    @Override
    public List getFinishedPlanList() {
        return planDAO.findFinishedList();
    }

    @Override
    @Transactional
    public void updatePlan(Plan plan) {
        planDAO.update(plan);
    }
}
