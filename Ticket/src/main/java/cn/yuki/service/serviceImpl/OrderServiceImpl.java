package cn.yuki.service.serviceImpl;

import cn.yuki.dao.*;
import cn.yuki.entity.*;
import cn.yuki.service.OrderService;
import cn.yuki.service.util.DateUtil;
import cn.yuki.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

@Component
public class OrderServiceImpl implements OrderService, Runnable {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private SeatDAO seatDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PurseDAO purseDAO;

    @Autowired
    private ReserveDAO reserveDAO;

    @Autowired
    private PlanDAO planDAO;

    @Autowired
    private HouseFinanceDAO houseFinanceDAO;

    @Autowired
    private UserFinanceDAO userFinanceDAO;

    private Thread thread;

    //空的延迟队列
    private DelayQueue<OrderDelayItem> item = new DelayQueue<>();
    private HashMap<Integer, String>  hashMap= new HashMap<>();

    public OrderServiceImpl(){
        thread = new Thread(this);
        thread.start();
    }

    /**
     * 向延迟队列中添加任务
     * @param orderID
     * @param time
     * @param timeUnit
     */
    public void put(int orderID, long time, TimeUnit timeUnit){
        System.out.println(orderID+" 被放进来了\n\n\n\n\n\n\n\n\n\n");
        long millieTime = TimeUnit.MILLISECONDS.convert(time, timeUnit);
        System.out.println(millieTime+"毫秒");
        OrderDelayItem t = new OrderDelayItem(orderID, millieTime);
        item.put(t);
    }

    /**
     * 从延迟队列中取出任务
     * @param orderID
     * @return
     */
    public  boolean remove(int orderID){
        System.out.println(orderID+" 被拿出去了\n\n\n\n\n\n\n\n\n\n");
        Iterator<OrderDelayItem> iterator=item.iterator();
        OrderDelayItem orderDelayItem;
        while(iterator.hasNext()){
            orderDelayItem =  iterator.next();
            if(orderDelayItem.getOrderID()==orderID){
                return item.remove(orderDelayItem);
            }
        }
        return false;
    }

    /**
     * 启动延迟队列
     */
    @Transactional
    public void execute(){
        System.out.println("开启线程\n\n\n\n\n\n\n\n\n\n");
        while(true){
            try{
                //从延迟队列中取值，如果一直没有对象过期则一直等待
                OrderDelayItem t1 = item.take();
                if(t1 != null){
                    Order order = orderDAO.find(t1.getOrderID());
                    order.setEndTime(new Date());
                    order.setState(2);
                    orderDAO.update(order);
                    System.out.println(order.getId()+"号订单过期");
                    //将座位从锁住状态改为空闲状态
                    unlock(order);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void unlock(Order order){
        String locked = hashMap.get(order.getPlanID());
        if(locked != null){
            String seats = order.getSeats();
            String[] seatArray = seats.split(",");
            if(seatArray.length != 0) {
                for (int i = 0; i < seatArray.length; i++) {
                    locked = locked.replace(seatArray[i] + ",", "");
                    locked = locked.replace("," + seatArray[i], "");
                }
            }else{
                locked = locked.replace(seats+",", "");
                locked = locked.replace("," + seats, "");
            }
            System.out.println(locked);
            hashMap.put(order.getPlanID(),locked);
        }
    }

    @Override
    @Transactional
    public int makeOrder(int planID, String planName, int userID, String username,
                         double totalPrice, double payment, String activity, String seats, double integration, int counter) {
        //先去检查座位有没有被锁定，如果锁住了就返回冲突
        String locked = hashMap.get(planID);
        if(locked == null){
            locked = "";
        }else{
            String[] seatArray = seats.split(",");
            for(int i=0;i<seatArray.length;i++){
                if(locked.contains(seatArray[i])){
                    return 0;
                }
            }
        }
        //如果没有锁住，就把它加入到等待队列
        hashMap.put(planID, locked+","+seats);
        //向数据库存入
        Order order = new Order();
        order.setPlanID(planID);
        order.setPlanName(planName);
        order.setUserID(userID);
        order.setUsername(username);
        order.setTotalPrice(totalPrice);
        order.setPayment(payment);
        order.setActivity(activity);
        order.setSeats(seats);
        order.setState(0);
        order.setNum(counter);
        Date date = new Date();
        order.setCreateTime(date);
        //3分钟后不付款则过期
        Date dateLater = new Date((date.getTime() + 3*60*1000));
        order.setOverdueTime(dateLater);

        int orderID = orderDAO.save(order);
        //放入延迟队列
        put(orderID, 3, TimeUnit.MINUTES);

        //更新用户的积分信息
        if(integration>0) {
            User user = userDAO.find(userID);
            user.setIntegration(0);
            userDAO.update(user);
        }
        return orderID;
    }

    @Override
    @Transactional
    public Result payOrder(Order order, int purseID) {
        //将座位从锁住状态改为已购买状态
        Seat seat =  seatDAO.find(order.getPlanID());
        if(seat!=null) {
            String seats = seat.getSeats();
            seats += "," + order.getSeats();
            seat.setSeats(seats);
            seatDAO.update(seat);
        }else{
            seat = new Seat();
            seat.setPlanId(order.getPlanID());
            seat.setSeats(order.getSeats());
            seatDAO.update(seat);
        }
        //将订单从延迟队列中取出来
        remove(order.getId());
        //将座位从锁住状态改为已购买状态
        unlock(order);
        //更新订单状态
        order.setPaymentTime(new Date());
        order.setEndTime(new Date());
        order.setOverdueTime(new Date());
        order.setState(1);
        order.setPurseID(purseID);
        orderDAO.update(order);
        //更新用户信息
        int userId  = order.getUserID();
        User user = userDAO.find(userId);
        user.setMoney(user.getMoney()+order.getPayment());
        user.setIntegration(user.getIntegration()+(int)order.getPayment());
        userDAO.update(user);
        //更新用户财务数据
        UserFinance userFinance = new UserFinance();
        userFinance.setUserId(userId);
        userFinance.setDate(new Date());
        userFinance.setMoney(0-order.getPayment());
        userFinance.setState(0);
        userFinanceDAO.saveOrUpdate(userFinance);
        //更新用户钱包信息
        Purse purse = purseDAO.findByUserId(userId);
        purse.setRemain(purse.getRemain()-order.getPayment());
        //更新场馆财务信息
        Plan plan = planDAO.find(String.valueOf(order.getPlanID()));
        HouseFinance houseFinance = new HouseFinance();
        houseFinance.setTime(new Date());
        houseFinance.setMoney(houseFinance.getMoney()+order.getPayment());
        houseFinance.setUserId(userId);
        houseFinance.setOrderId(order.getId());
        houseFinance.setPlanId(order.getPlanID());
        houseFinance.setHouseId(Integer.parseInt(plan.getHouseID()));
        houseFinance.setState(0);
        houseFinanceDAO.saveOrUpdate(houseFinance);
        return Result.Success;
    }

    @Override
    public String getUnavailableSeats(int planId){
        String result = "";
        //数据库里的已购买座
        Seat seat = seatDAO.find(planId);
        if(seat!=null) {
            result += seat.getSeats();
        }
        //队列中被锁住的座位
        String locked = hashMap.get(planId);
        if(locked != null){
            result+=locked;
        }
        return result;
    }

    @Override
    public Order getOrderInfo(int orderId) {
        return orderDAO.find(orderId);
    }

    @Override
    public List getOrderList(int userId) {
        return orderDAO.userFindList(userId);
    }

    @Override
    public List getOrderListByState(int userId, String state) {
        return orderDAO.userfindListByState(userId,state);
    }

    @Override
    public List houseGetOrderList(int houseId) {
        return orderDAO.houseFindList(houseId);
    }

    @Override
    public List houseGetOrderListByState(int houseId, String state) {
        return orderDAO.houseFindListByState(houseId,state);
    }

    @Override
    public Result cancleOrder(int orderId) {
     Order order = orderDAO.find(orderId);
        Result result;
        if(order.getState()==0){
            result = cancleNotPayedOrder(order);
        }else if(order.getState()==1){
            result = canclePayedOrder(order);
        }else{
            result = Result.WrongOrderState;
        }
        return result;
    }


    @Transactional
    public Result cancleNotPayedOrder(Order order){
        //这里的取消订单是取消未付款的订单，而不是已经付款的
        order.setEndTime(new Date());
        order.setState(2);
        orderDAO.update(order);

        //将座位释放出来
        unlock(order);

        return Result.Success;
    }


    @Transactional
    /**
     * 根据时间计算退回金额 演出当天不能退票，演出前一周内退50%，演出前2周内退85%
     */
    public Result canclePayedOrder(Order order) {
        String orderSeats = order.getSeats();

        Plan plan =  planDAO.find(String.valueOf(order.getPlanID()));
        Date startTime =plan.getStartTime();
        Date oneWeekBefore = DateUtil.getDateWeekBefore(startTime,1);
        Date oneDayBefore = DateUtil.getDateDayBefore(startTime,1);
        Date now = new Date();

        if(now.before(oneWeekBefore)){
            //退85%
            returnMoney(order,plan,0.85);
            //将座位释放出来
            releaseSeats(plan.getId(),orderSeats);
            order.setState(-1);
            order.setEndTime(new Date());
            order.setActivity(order.getActivity()+"\n演出前两周内退票，退回85%");
            orderDAO.update(order);

            return Result.Success;
        }else if(now.before(oneDayBefore)){
            //退50%
            returnMoney(order,plan,0.5);
            //将座位释放出来
            releaseSeats(plan.getId(),orderSeats);

            order.setState(-1);
            order.setEndTime(new Date());
            order.setActivity(order.getActivity()+"\n演出前一周内退票，退回50%");
            orderDAO.update(order);
            return Result.Success;
        }else{
            //离演出开始只有一天不能退票
            return Result.TimeConflict;
        }
    }

    @Override
    @Transactional
    public Result orderOnSite(int planID, String planName, int userID, String username,
                              double totalPrice, double payment, String activity, String seats,int houseID) {
        //先去检查座位有没有被锁定，如果锁住了就返回冲突
        String locked = getUnavailableSeats(planID);
        if(locked == null){
            locked = "";
        }else{
            String[] seatArray = seats.split(",");
            for(int i=0;i<seatArray.length;i++){
                if(locked.contains(seatArray[i])){
                    return Result.SeatConflict;
                }
            }
        }
        int orderID=0;
        if(userID!=0) {
            //写入数据库的订单
            Order order = new Order();
            order.setPlanID(planID);
            order.setPlanName(planName);
            order.setUserID(userID);
            order.setUsername(username);
            order.setTotalPrice(totalPrice);
            order.setPayment(payment);
            order.setActivity(activity);
            order.setSeats(seats);
            order.setState(1);
            order.setCreateTime(new Date());
            order.setEndTime(new Date());
            order.setPaymentTime(new Date());
            order.setOverdueTime(new Date());
            orderID = orderDAO.save(order);

            //更新用户财务信息
            UserFinance userFinance = new UserFinance();
            userFinance.setUserId(userID);
            userFinance.setDate(new Date());
            userFinance.setMoney(0-payment);
            userFinanceDAO.saveOrUpdate(userFinance);
        }
        //更新场馆财务信息
        HouseFinance houseFinance = new HouseFinance();
        houseFinance.setOrderId(orderID);
        houseFinance.setHouseId(houseID);
        houseFinance.setPlanId(planID);
        houseFinance.setTime(new Date());
        houseFinance.setMoney(payment);
        houseFinance.setUserId(userID);
        houseFinance.setState(2);
        houseFinanceDAO.saveOrUpdate(houseFinance);
        //更新seat表
        Seat seat = seatDAO.find(planID);
        if(seat==null){
            seat = new Seat();
            seat.setPlanId(planID);
            seat.setSeats(seats);
        }else {
            seat.setSeats(seat.getSeats() + "," + seats);
        }
        seatDAO.update(seat);
        //更新用户积分
        if(userID!=0){
            User user = userDAO.find(userID);
            user.setIntegration(user.getIntegration()+(int)payment);
            user.setMoney(user.getMoney()+payment);
            userDAO.update(user);
        }
        return Result.Success;
    }

    public void returnMoney(Order order, Plan plan, double discount){
        HouseFinance houseFinance = new HouseFinance();
        houseFinance.setPlanId(plan.getId());
        houseFinance.setOrderId(order.getId());
        houseFinance.setHouseId(Integer.parseInt(plan.getHouseID()));
        houseFinance.setUserId(order.getUserID());
        houseFinance.setTime(new Date());
        houseFinance.setMoney(0-order.getPayment()*discount);
        houseFinance.setState(-1);
        houseFinanceDAO.saveOrUpdate(houseFinance);

        UserFinance userFinance = new UserFinance();
        userFinance.setUserId(order.getUserID());
        userFinance.setDate(new Date());
        userFinance.setMoney(order.getPayment()*discount);
        userFinance.setState(2);
        userFinanceDAO.saveOrUpdate(userFinance);

        Purse purse = purseDAO.findByUserId(order.getUserID());
        purse.setRemain(purse.getRemain()+order.getPayment()*discount);
        purseDAO.update(purse);
    }

    public void releaseSeats(int planId, String orderSeats){
        Seat seat = seatDAO.find(planId);
        String seats = seat.getSeats();
        String[] temp = orderSeats.split(",");
        if(temp.length==0){
            seats = seats.replace(orderSeats+",","");
            seats = seats.replace(","+orderSeats,"");
        }else{
            for(int i=0;i<temp.length;i++){
                seats = seats.replace(temp[i]+",","");
                seats = seats.replace(","+temp[i],"");
            }
        }
        seat.setSeats(seats);
        seatDAO.update(seat);
    }

    @Override
    @Transactional
    public Result reserve(int planID, String planName, int userID, String username,
                          int purseId, double payment, String planTime, int num, String type) {
        //将预订保存在数据库里
        Reserve reserve = new Reserve();
        reserve.setPlanID(planID);
        reserve.setPlanName(planName);
        reserve.setUserID(userID);
        reserve.setUsername(username);
        reserve.setPurseID(purseId);
        reserve.setPayment(payment);
        reserve.setNum(num);
        reserve.setType(type);
        reserve.setCreateTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date planTimeDate = sdf.parse(planTime);
            reserve.setPlanTime(planTimeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        reserveDAO.saveOrUpdate(reserve);

        UserFinance userFinance = new UserFinance();
        userFinance.setUserId(userID);
        userFinance.setDate(new Date());
        userFinance.setMoney(-payment);
        userFinance.setState(1);
        userFinanceDAO.saveOrUpdate(userFinance);

        //去用户钱包里扣钱
        Purse purse = purseDAO.findByUserId(userID);
        purse.setRemain(purse.getRemain()-payment);
        return Result.Success;
    }

    @Override
    public Result checkIn(int orderId) {
        Order order = orderDAO.find(orderId);
        if(order == null){
            return Result.Invalid;
        }
        if(order.getState() == 3){
            return Result.HaveChecked;
        }else if(order.getState() == 1){
            order.setState(3);
            order.setEndTime(new Date());
            orderDAO.update(order);
            return Result.Success;
        }else{
            return Result.Invalid;
        }
    }

    @Override
    public void run() {
        execute();
    }
}