package cn.yuki.service;

import cn.yuki.service.util.Result;
import org.json.JSONObject;

public interface ManagerService {
    public String getStatistic();

    public void insertWebsiteIncome(double money, int planId, int houseId);
}
