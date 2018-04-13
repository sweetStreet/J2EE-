package cn.yuki.entity;

import javax.persistence.*;


@Entity
@Table(name="houses")
public class House {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    //密码
    private String password;
    //管理员邮箱
    private String email;
    //名称
    private String name;
    //所在城市
    private String city;
    //详细地址
    private String location;
    //行
    private String row;
    //列
    private String line;
    //存储图片路径
    private String path;
    //是否通过manager审核
    //这个变量在当初命名的时候有点问题，实际上是表达的状态信息
    //-1表示拒绝 0表示注册未审核 1表示已审核 2表示修改未审核
    private int isValid;


    public House(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIsValid() {
        return isValid;
    }


    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    @Transient
    public String getNo() {
        return String.valueOf(1000000+id);
    }

    @Transient
    public String getStateText(){
        if(isValid==1){
            return "已审核";
        }else if(isValid == 0){
            return "未审核";
        }else if(isValid == -1){
            return "已拒绝";
        }else{
            return "";
        }
    }

    @Transient
    public String getSeats() {
        int rows = Integer.parseInt(row);
        int lines = Integer.parseInt(line);
        String seat = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < lines; j++) {
                seat += "a";
            }
            if (i != rows - 1) {
                seat += ",";
            }
        }
        return seat;
    }

}
