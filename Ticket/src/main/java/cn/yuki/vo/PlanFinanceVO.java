package cn.yuki.vo;

public class PlanFinanceVO {
    String planName;
    double money;

    public PlanFinanceVO(String planName, double money) {
        this.planName = planName;
        this.money = money;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
