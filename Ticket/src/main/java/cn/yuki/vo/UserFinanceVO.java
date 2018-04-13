package cn.yuki.vo;

public class UserFinanceVO {
    String email;
    double money;

    public UserFinanceVO(String email, double money) {
        this.email = email;
        this.money = money;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
