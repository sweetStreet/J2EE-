package cn.yuki.service;

import cn.yuki.entity.Plan;
import cn.yuki.service.util.Result;

import java.util.ArrayList;
import java.util.List;

public interface PlanService {

    public Result makePlan(String name, String startTime, String endTime, String type, String description,
                           String price, String filelocation, String houseID, String seatmap);

    public List getPlan(String type);

    public Plan getPlanDetail(String id);

    public List getPlanByHouse(int houseId);

    public List getFinishedPlanList();

    void updatePlan(Plan plan);
}
