package cn.yuki.service.serviceImpl;

import cn.yuki.dao.HouseDAO;
import cn.yuki.dao.HouseFinanceDAO;
import cn.yuki.dao.OrderDAO;
import cn.yuki.dao.PlanDAO;
import cn.yuki.entity.House;
import cn.yuki.entity.Plan;
import cn.yuki.service.HouseService;
import cn.yuki.service.util.MD5;
import cn.yuki.service.util.MailUtil;
import cn.yuki.service.util.Result;
import cn.yuki.vo.HouseFinanceRatioVO;
import cn.yuki.vo.HouseFinanceVO;
import cn.yuki.vo.PlanFinanceVO;
import cn.yuki.vo.UserFinanceVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("houseService")
public class HouseServiceImpl implements HouseService {

    @Autowired
    PlanDAO planDAO;

    @Autowired
    HouseDAO houseDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    HouseFinanceDAO houseFinanceDAO;


    @Transactional
    public int register(String name, String location, String rows, String lines, String email) {
        House house = new House();
        house.setName(name);
        house.setRow(rows);
        house.setLine(lines);
        house.setEmail(email);
        house.setLocation(location);
        house.setIsValid(0);
        return houseDAO.save(house);
    }

    @Override
    public Result login(String account, String password) {
        String id = String.valueOf(Integer.valueOf(account)-1000000);
        House house = houseDAO.find(id);
        if(house==null){
            return Result.NotRegistered;
        }
        if(house.getPassword().equals(MD5.MD5(password))){
            return Result.Success;
        }else{
            return Result.WrongPassword;
        }
    }

    @Override
    public List getAllHouse(String state) {
        List<House> list;
        //获得所有状态的信息
        if(state == null || state.equals("")){
            list = houseDAO.findAll();
            //获得待审核的剧场的信息
        }else if(state.equals("0")){
            list = houseDAO.findByState("0");
            //获得已经审核通过的剧场信息
        }else if(state.equals("1")){
            list = houseDAO.findByState("1");
            //获得审核被拒绝的剧场信息
        }else if(state.equals("-1")){
            list = houseDAO.findByState("-1");
        }
        else{
            return null;
        }
        return list;
    }

    @Override
    @Transactional
    public void passHouse(String id, int state) {
        //发邮件告知剧场管理员，7位账号
        House house= houseDAO.find(id);
        String no = house.getNo();
        if(state == 0) {
            MailUtil.singleMail(house.getEmail(), "Tickets场馆申请/修改成功",
                    "您的7位登录账号为：" + no + "，您的初始密码为当前7位账号，请尽快登录后修改");
            house.setPassword(MD5.MD5(no));
        }else if(state == 2){
            MailUtil.singleMail(house.getEmail(), "Tickets场馆申请/修改成功",
                    "您的账号为：" + no + "的场馆修改信息已通过审核");
        }
        house.setIsValid(1);
        houseDAO.update(house);
    }

    @Override
    @Transactional
    public void rejectHouse(String id, String reason) {
        //发邮件告知拒绝和原因
        House house= houseDAO.find(id);
        MailUtil.singleMail(house.getEmail(), "Tickets场馆申请失败","管理员拒绝了您的申请: "+reason);
        house.setIsValid(-1);
        houseDAO.update(house);
    }

    @Override
    public House getHouseInfo(String account) {
        String id = String.valueOf(Integer.parseInt(account)-1000000);
        return houseDAO.find(id);
    }

    @Override
    public House getHouseInfo(int id) {
        return houseDAO.find(String.valueOf(id));
    }

    @Override
    public String getStatistic(int houseId) {
        List houseInfo = houseFinanceDAO.findByHouseId(houseId);
        List checkInfo = planDAO.findByHouse(houseId);
        String planXAxis = "";
        String planYAxis = "";
        String checkXAxis = "";
        String checkYAxis = "";
        for(int i=0;i<houseInfo.size();i++){
            planXAxis+=((PlanFinanceVO)houseInfo.get(i)).getPlanName()+",";
            double money = ((PlanFinanceVO)houseInfo.get(i)).getMoney();
            if(money<0){
                money = -money;
            }
            planYAxis+=money+",";
        }
        for(int i=0;i<checkInfo.size();i++){
            if(((Plan)checkInfo.get(i)).getCheckMoney()!=0) {
                checkXAxis += ((Plan) checkInfo.get(i)).getName() + ",";
                double checkMoney = ((Plan) checkInfo.get(i)).getCheckMoney();
                if(checkMoney<0){
                    checkMoney = -checkMoney;
                }
                checkYAxis +=  checkMoney+ ",";
            }
        }
        if(planXAxis.length()>0) {
            planXAxis = planXAxis.substring(0, planXAxis.length() - 1);
        }
        if(planYAxis.length()>0) {
            planYAxis = planYAxis.substring(0, planYAxis.length() - 1);
        }
        if(checkXAxis.length()>0) {
            checkXAxis = checkXAxis.substring(0, checkXAxis.length() - 1);
        }
        if(checkYAxis.length()>0) {
            checkYAxis = checkYAxis.substring(0, checkYAxis.length() - 1);
        }

        List houseRatio = houseFinanceDAO.getStatistic(houseId);
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<houseRatio.size();i++){
            HouseFinanceRatioVO h = (HouseFinanceRatioVO)houseRatio.get(i);
            JSONObject object = new JSONObject();
            double sum = h.getSum();
            if(sum<0){
                sum = -sum;
            }
            object.put("value",sum);
            object.put("name",h.getStateString());
            jsonArray.put(i,object);
        }

//        double online;
//        double onsite;
//        double reserve;
//        double back;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("planXAxis",planXAxis);
        jsonObject.put("planYAxis",planYAxis);
        jsonObject.put("checkXAxis",checkXAxis);
        jsonObject.put("checkYAxis",checkYAxis);
        jsonObject.put("pie",jsonArray);
        return jsonObject.toString();
    }

    @Override
    @Transactional
    public Result reviseHouse(String houseId, String name, String location, String row, String line, String email) {
        House house = houseDAO.find(houseId);
        house.setName(name);
        house.setRow(row);
        house.setLine(line);
        house.setEmail(email);
        house.setLocation(location);
        house.setIsValid(2);
        houseDAO.update(house);
        return Result.Success;
    }

    @Override
    @Transactional
    public Result reviseHousePassword(String houseId, String password) {
        House house = houseDAO.find(houseId);
        house.setPassword(MD5.MD5(password));
        houseDAO.update(house);
        return Result.Success;
    }
}
