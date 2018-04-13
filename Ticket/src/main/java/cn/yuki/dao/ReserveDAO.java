package cn.yuki.dao;

import cn.yuki.entity.Reserve;

import java.util.List;

public interface ReserveDAO {

    void saveOrUpdate(Reserve reserve);

    List findTwoWeekLater();

    List findReserves(int houseId);
}
