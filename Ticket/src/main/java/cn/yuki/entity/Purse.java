package cn.yuki.entity;

import javax.persistence.*;

@Entity
@Table(name="purses")
public class Purse {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    //用户ID
    private int userId;
    //账户名称
    private String name;
    //余额
    private double remain;
    //支付密码
    private String password;

    public Purse(){

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
