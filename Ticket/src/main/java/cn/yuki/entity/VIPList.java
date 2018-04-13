package cn.yuki.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name="vips")
public class VIPList implements Serializable{
    List<VIP> vips;

    @XmlElement(name="vip")
    public List<VIP> getVips(){
        return vips;
    }

    public void setVips(List<VIP> vips){
        this.vips = vips;
    }
}
