package cn.yuki.entity;


import cn.yuki.service.util.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="plans")
public class Plan {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    //名称
    private String name;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //价格，多种价格用逗号分隔
    private String price;
    //类型
    private String type;
    //描述
    private String description;
    //剧院编号
    private String houseID;
    //图片地址
    private String fileLocation;
    //座位图
    private String seatmap;
    //最后结算到的钱
    private double checkMoney;

    public Plan() {
    }

    public Plan(String name, Date startTime, Date endTime, String price, String description, String houseID,  double checkMoney) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.description = description;
        this.houseID = houseID;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getSeatmap() {
        return seatmap;
    }

    public void setSeatmap(String seatmap) {
        this.seatmap = seatmap;
    }

    public double getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(double checkMoney) {
        this.checkMoney = checkMoney;
    }

    @Transient
    //0表示已经结束
    //1表示2周内演出，接受在线选座
    //2表示演出开始还有很多天，只接受预订
    public int getState(){
        if(DateUtil.getDateWeekBefore(startTime,2).after(new Date())){
            return 2;
        }else if(startTime.before(new Date())){
            return 0;
        }else{
            return 1;
        }
    }
    @Transient
    public JSONObject getPriceList(){
        JSONObject jsonObject = new JSONObject();
        JSONObject result = new JSONObject();
        String[] prices = price.split(",");
        for(int i=0;i<prices.length;i++){
            if(i == 0){
                jsonObject.put("price", prices[0]);
                jsonObject.put("classes", "first-seat");
                result.put("a", jsonObject);
            }else if(i == 1){
                jsonObject = new JSONObject();
                jsonObject.put("price", prices[1]);
                jsonObject.put("classes", "second-seat");
                result.put("b", jsonObject);
            }else if(i == 2){
                jsonObject = new JSONObject();
                jsonObject.put("price", prices[2]);
                jsonObject.put("classes", "third-seat");
                result.put("c", jsonObject);
            }else if(i == 3){
                jsonObject = new JSONObject();
                jsonObject.put("price", prices[3]);
                jsonObject.put("classes", "forth-seat");
                result.put("d", jsonObject);
            }
        }
        return result;
    }

    @Transient
    public String getLegned(){
        JSONArray jsonArray1 = new JSONArray();
        jsonArray1.put(0,"a");
        jsonArray1.put(1,"available");
        JSONArray jsonArray2 = new JSONArray();
        jsonArray2.put(0,"b");
        jsonArray2.put(1,"available");
        JSONArray jsonArray3 = new JSONArray();
        jsonArray3.put(0,"c");
        jsonArray3.put(1,"available");
        JSONArray jsonArray4 = new JSONArray();
        jsonArray4.put(0,"d");
        jsonArray4.put(1,"available");
        JSONArray result = new JSONArray();

        String[] prices = price.split(",");

        if(prices.length == 0){
            jsonArray1.put(2,price);
            result.put(0,jsonArray1);
            return result.toString();
        }
        for(int i=0;i<prices.length;i++){
            if(i == 0){
                jsonArray1.put(2,prices[i]);
                result.put(i,jsonArray1);
            }else if(i==1){
                jsonArray2.put(2,prices[i]);
                result.put(i,jsonArray2);
            }else if(i==2){
                jsonArray3.put(2,prices[i]);
                result.put(i,jsonArray3);
            }else if(i==3){
                jsonArray4.put(2,prices[i]);
                result.put(i,jsonArray4);
            }
        }
        return result.toString();
    }
}
