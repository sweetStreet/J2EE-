package cn.yuki.vo;

import javax.persistence.Entity;
import java.util.Date;

public class PlanVO {
    //planId
    private int id;
    //名称
    private String name;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //剧院编号
    private String houseID;
    //剧场名称
    private String houseName;
    //当前plan数据库里的结算金额，如果是0表示还没结算，如果比0大表示已经完成结算
    private double money;
    //场馆目前结算到的钱
    private double checkMoney;

    public PlanVO(){

    }

    public PlanVO(int id, String name, Date startTime, Date endTime, String houseID, String houseName, double money, double checkMoney) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.houseID = houseID;
        this.houseName = houseName;
        this.money = money;
        this.checkMoney = checkMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(double checkMoney) {
        this.checkMoney = checkMoney;
    }
}
