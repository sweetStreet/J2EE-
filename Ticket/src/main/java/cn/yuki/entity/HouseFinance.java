package cn.yuki.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="finances")
public class HouseFinance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    //场馆id
    private int houseId;
    //计划id
    private int planId;
    //orderid 当orderid为0时表示是非会员购买的
    private int orderId;
    //userid 当userid为0时表示是非会员购买的
    private int userId;
    //收支情况，正数表示收入，负数表示支出
    private double money;
    //时间
    private Date time;
    //0表示在线购买，1表示预约购买, 2表示现场购买，-1表示退款
    private int state;

    public HouseFinance(){

    }

    public HouseFinance(int houseId, double money){
        this.houseId = houseId;
        this.money = money;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
