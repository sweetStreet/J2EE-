package cn.yuki.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="reserves")
public class Reserve {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    //计划ID
    private int planID;
    //计划名称
    private String planName;
    //用户id
    private int userID;
    //用户名
    private String username;
    //支付账号
    private int purseID;
    //预付款
    private double payment;
    //预订创建时间
    private Date createTime;
    //订单开始时间
    private Date planTime;
    //座位数量
    private int num;
    //座位类型 a/b/c/d
    private String type;

    public Reserve() {
    }

    public Reserve(int planID, String planName, double payment){
        this.planID = planID;
        this.planName = planName;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPurseID() {
        return purseID;
    }

    public void setPurseID(int purseID) {
        this.purseID = purseID;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
