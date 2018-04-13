package cn.yuki.service.serviceImpl;

import cn.yuki.dao.HouseFinanceDAO;
import cn.yuki.dao.UserFinanceDAO;
import cn.yuki.dao.WebsiteFinanceDAO;
import cn.yuki.entity.WebsiteFinance;
import cn.yuki.service.ManagerService;
import cn.yuki.vo.HouseFinanceVO;
import cn.yuki.vo.UserFinanceVO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    HouseFinanceDAO houseFinanceDAO;

    @Autowired
    UserFinanceDAO userFinanceDAO;

    @Autowired
    WebsiteFinanceDAO websiteFinanceDAO;

    @Override
    public String getStatistic() {
        List houseInfo = houseFinanceDAO.find();
        List userInfo = userFinanceDAO.find();
        String houseXAxis = "";
        String houseYAxis = "";
        String userXAxis = "";
        String userYAxis = "";
        for(int i=0;i<houseInfo.size();i++){
            houseXAxis+=((HouseFinanceVO)houseInfo.get(i)).getHouseName()+",";
            houseYAxis+=((HouseFinanceVO)houseInfo.get(i)).getMoney()+",";
        }
        for(int i=0;i<userInfo.size();i++){
            userXAxis+=((UserFinanceVO)userInfo.get(i)).getEmail()+",";
            userYAxis+=((UserFinanceVO)userInfo.get(i)).getMoney()+",";
        }

        if(houseXAxis.length()>0) {
            houseXAxis = houseXAxis.substring(0, houseXAxis.length() - 1);
        }
        if(houseYAxis.length()>0) {
            houseYAxis = houseYAxis.substring(0, houseYAxis.length() - 1);
        }
        if(userXAxis.length()>0) {
            userXAxis = userXAxis.substring(0, userXAxis.length() - 1);
        }
        if(userYAxis.length()>0) {
            userYAxis = userYAxis.substring(0, userYAxis.length() - 1);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("houseXAxis",houseXAxis);
        jsonObject.put("houseYAxis",houseYAxis);
        jsonObject.put("userXAxis",userXAxis);
        jsonObject.put("userYAxis",userYAxis);
        return jsonObject.toString();
    }

    @Override
    @Transactional
    public void insertWebsiteIncome(double money, int planId, int houseId) {
        WebsiteFinance websiteFinance = new WebsiteFinance();
        websiteFinance.setCreateTime(new Date());
        websiteFinance.setHouseId(houseId);
        websiteFinance.setPlanId(planId);
        websiteFinance.setIncome(money);
        System.out.println("insertWebsiteIncome!!!\n\n\n");
        websiteFinanceDAO.saveOrUpdate(websiteFinance);
    }
}
