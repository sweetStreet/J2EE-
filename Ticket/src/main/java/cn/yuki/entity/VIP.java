package cn.yuki.entity;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class VIP implements Serializable{
    private int level;
    private double discount;
    private double money;

    public VIP() {
    }

    @XmlElement(name="level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @XmlElement(name="discount")
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @XmlElement(name="money")
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
