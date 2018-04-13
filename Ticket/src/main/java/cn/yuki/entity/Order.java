package cn.yuki.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    //计划id
    private int planID;
    //计划名称
    private String planName;
    //用户id
    private int userID;
    //用户名 todo 我觉得我非常有必要把username改成email
    private String username;
    //支付账号
    private int purseID;
    //应付款
    private double totalPrice;
    //实付款
    private double payment;
    //优惠活动
    private String activity;
    //订单创建时间
    private Date createTime;
    //订单超时时间
    private Date overdueTime;
    //订单付款时间
    private Date paymentTime;
    //所在场馆id
    private int houseID;
    //数量
    private int num;
    //所在场馆的地址
    private String address;
    //订单关闭／完成时间
    private Date endTime;
    //状态 0表示未付款／1表示已付款／2表示未付款关闭/-1表示已退款/3表示已经检票
    private int state;
    //座位信息
    private String seats;


    public Order(){

    }

    public Order(int id, int planID, String planName, double payment, Date createTime, int state){
        this.id = id;
        this.planID = planID;
        this.planName = planName;
        this.payment = payment;
        this.createTime = createTime;
        this.state = state;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(Date overdueTime) {
        this.overdueTime = overdueTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Transient
    public long getOverdue(){
        return overdueTime.getTime();
    }
}
