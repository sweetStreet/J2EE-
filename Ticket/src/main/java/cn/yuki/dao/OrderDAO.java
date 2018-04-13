package cn.yuki.dao;

import cn.yuki.entity.Order;

import java.util.List;

public interface OrderDAO {

    public int save(Order order);

    public void update(Order order);

    public Order find(int id);

    public List<Order> userFindList(int userId);

    public List<Order> userfindListByState(int userId, String state);

    public List<Order> houseFindList(int houseId);

    public List<Order> houseFindListByState(int houseId, String state);


}
