package cn.yuki.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="wfinances")
public class WebsiteFinance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    //计划id
    private int planId;
    //houseid
    private int houseId;
    //收入
    private double income;
    //创建时间
    private Date createTime;

    public WebsiteFinance() {

    }

    public WebsiteFinance(int houseId, double income) {
        this.houseId = houseId;
        this.income = income;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
}
