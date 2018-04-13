package cn.yuki.vo;

public class HouseFinanceVO {
    String houseName;
    double money;

    public HouseFinanceVO(String houseName, double money){
        this.houseName = houseName;
        this.money = money;
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
}
