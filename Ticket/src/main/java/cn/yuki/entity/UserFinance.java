package cn.yuki.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="ufinances")
public class UserFinance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    //用户id
    private int userId;
    //支出记为负，收入记为正
    private double money;
    //时间
    private Date date;
    //状态 0表示在线选座，1表示预订，2表示退款
    private int state;

    public UserFinance() {
    }

    public UserFinance(int userId, double money) {
        this.userId = userId;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
