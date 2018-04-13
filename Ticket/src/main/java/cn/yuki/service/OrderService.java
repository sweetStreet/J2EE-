package cn.yuki.service;

import cn.yuki.entity.Order;
import cn.yuki.entity.User;
import cn.yuki.service.util.Result;

import java.util.Date;
import java.util.List;

public interface OrderService {

    public int makeOrder(int planID, String planName, int userID, String username,
                         double totalPrice, double payment, String activity, String seats, double integration, int counter);

    public Result payOrder(Order order, int userId);

    public String getUnavailableSeats(int planId);

    public Order getOrderInfo(int orderId);

    public List getOrderList(int userId);

    public List getOrderListByState(int userId, String state);

    public List houseGetOrderList(int houseId);

    public List houseGetOrderListByState(int houseId, String state);

    public Result cancleOrder(int orderId);

    public Result orderOnSite(int planID, String planName, int userID, String username,
                              double totalPrice, double payment, String activity, String seats, int houseID);

    public Result reserve(int planID, String planName, int userID, String username, int purseId,
                          double payment, String planTime, int num, String type);

    public Result checkIn(int orderId);
}
