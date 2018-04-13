package cn.yuki.vo;

public class HouseFinanceRatioVO {
    int state;
    double sum;

    public HouseFinanceRatioVO(int state, double sum) {
        this.state = state;
        this.sum = sum;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getStateString(){
        if(state == 0){
            return "在线购买";
        }else if(state == 1){
            return "预约购买";
        }else if(state == 2){
            return "现场购买";
        }else if(state == -1){
            return "退款";
        }else{
            return "";
        }
    }
}
