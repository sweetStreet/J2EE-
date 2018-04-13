package cn.yuki.entity;

import javax.persistence.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    //邮箱
    private String email;
    //密码
    private String password;
    //用户名
    private String username;
    //是否是经理，0表示不是，1表示是
    private int isManager;
    //会员积分
    private int integration;
    //消费的金额
    private double money;
    //状态，是否激活
    private int activate;
    //激活码
    private String validateCode;
    //注册时间
    private Date registerTime;
    //账户是否被删除
    private int isDeleted;

    public User() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIsManager() {
        return isManager;
    }

    public void setIsManager(int isManager) {
        this.isManager = isManager;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getActivate() {
        return activate;
    }

    public void setActivate(int activate) {
        this.activate = activate;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Transient
    public Date getLastActivateTime(){
        Calendar c1 = Calendar.getInstance();
        c1.setTime(registerTime);
        c1.add(Calendar.DATE, 2);
        return c1.getTime();
    }

    @Transient
    public VIP getVIP() {
        try {
            String xmlPath = "/Users/liuyu/Downloads/Ticket/target/classes/vip.xml";
            JAXBContext context = JAXBContext.newInstance(VIPList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object object = unmarshaller.unmarshal(new File(xmlPath));
            VIPList vips = (VIPList) object;
            List<VIP> vipList = vips.getVips();

            for(int i=0;i<vipList.size();i++){
                if(money<vipList.get(i).getMoney()){
                    return vipList.get(i-1);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return new VIP();
    }

}
