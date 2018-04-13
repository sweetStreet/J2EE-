package cn.yuki.service.serviceImpl;


import cn.yuki.dao.*;
import cn.yuki.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class DeliverOrderQtz {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    ReserveDAO reserveDAO;
    @Autowired
    SeatDAO seatDAO;
    @Autowired
    PlanDAO planDAO;
    @Autowired
    PurseDAO purseDAO;
    @Autowired
    HouseFinanceDAO houseFinanceDAO;
    @Autowired
    UserFinanceDAO userFinanceDAO;

    /**
     * 每天的早晨8点
     * 遍历数据库，开始自动分配座位
     */
    @Transactional
    protected void execute()  {
        //分配座位 成功——存进订单数据库，更新seat数据库，更新用户信息（积分）
        //失败——退钱更新用户账户信息
        List<Reserve> list = reserveDAO.findTwoWeekLater();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                Reserve reserve = list.get(i);
                int num = reserve.getNum();
                char type = reserve.getType().charAt(0);
                //todo 可能还要去检查一下队列中有没有正在等待的？？？
                Seat seatObject = seatDAO.find(reserve.getPlanID());
                String seatReserved = seatObject.getSeats();
                Plan plan = planDAO.find(String.valueOf(reserve.getPlanID()));
                String seatmap = plan.getSeatmap();
                int row = 1;
                int col = 1;
                String seats = "";
                for(int j =0;j<seatmap.length();j++){
                    if(seatmap.charAt(j)==','){
                        col++;
                        row = 1;
                    }
                    if(seatmap.charAt(j)==type){
                        String temp = row+"_"+col;
                        if(!seatReserved.contains(temp)){
                            seats+=temp+",";
                            num--;
                        }
                    }
                    if(num==0){
                        seats = seats.substring(0,seats.length()-1);
                        break;
                    }
                }
                //配票成功
                if(num==0){
                    Order order = new Order();
                    order.setPlanID(reserve.getPlanID());
                    order.setPlanName(reserve.getPlanName());
                    order.setUserID(reserve.getUserID());
                    order.setUsername(reserve.getUsername());
                    order.setPurseID(reserve.getPurseID());
                    order.setTotalPrice(reserve.getPayment());
                    order.setPayment(reserve.getPayment());
                    order.setActivity("自动分配座位无折扣");
                    order.setPaymentTime(new Date());
                    order.setEndTime(new Date());
                    order.setCreateTime(new Date());
                    order.setState(1);
                    order.setSeats(seats);
                    orderDAO.update(order);

                    seatReserved+=","+seats;
                    seatObject.setSeats(seatReserved);
                    seatDAO.update(seatObject);

                    User user = userDAO.find(reserve.getUserID());
                    user.setIntegration((int)reserve.getPayment());
                    user.setMoney(user.getMoney()+reserve.getPayment());

//                    //这里只有真正预订成功的才会被记录到userFinance中
//                    UserFinance userFinance = new UserFinance();
//                    userFinance.setUserId(reserve.getUserID());
//                    userFinance.setDate(new Date());
//                    userFinance.setMoney(reserve.getPayment());
//                    userFinanceDAO.saveOrUpdate(userFinance);
                }else{//配票失败
                    //退钱
                    Purse purse = purseDAO.findByUserId(reserve.getUserID());
                    purse.setRemain(purse.getRemain()+reserve.getPayment());
                    //更新用户财务信息
                    UserFinance userFinance = new UserFinance();
                    userFinance.setState(2);
                    userFinance.setDate(new Date());
                    userFinance.setMoney(reserve.getPayment());
                    userFinance.setUserId(reserve.getUserID());
                    userFinanceDAO.saveOrUpdate(userFinance);
                    //更新场馆的财务信息
                    HouseFinance houseFinance = new HouseFinance();
                    houseFinance.setMoney(0-reserve.getPayment());
                    houseFinance.setTime(new Date());
                    houseFinance.setUserId(reserve.getUserID());
                    houseFinance.setHouseId(Integer.parseInt(plan.getHouseID()));
                    houseFinance.setPlanId(plan.getId());
                    houseFinance.setState(1);
                    houseFinanceDAO.saveOrUpdate(houseFinance);
                }
            }
        }
    }
}
