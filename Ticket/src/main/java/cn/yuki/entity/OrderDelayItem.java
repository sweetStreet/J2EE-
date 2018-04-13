package cn.yuki.entity;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class OrderDelayItem implements Delayed {

    private int orderID;
    private long expTime; //过期时间


    public OrderDelayItem(int orderID,long timeout){
        this.orderID = orderID;
        this.expTime = timeout + System.currentTimeMillis();
    }

    /**
     * 需要实现的接口，获得延迟时间，过期时间-当前时间
     * @param unit
     * @return
     */
    public long getDelay(TimeUnit unit) {
        return expTime - System.currentTimeMillis();
    }

    /**
     * 延迟队列内部比较排序，当前时间的延迟时间-比较对象的延迟时间
     * @param o
     * @return
     */
    public int compareTo(Delayed o) {
        return Long.valueOf(this.expTime).compareTo(Long.valueOf(((OrderDelayItem) o).expTime));
    }

    public int getOrderID(){
        return orderID;
    }
}
