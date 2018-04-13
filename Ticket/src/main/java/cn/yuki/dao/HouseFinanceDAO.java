package cn.yuki.dao;

import cn.yuki.entity.HouseFinance;

import java.util.List;

public interface HouseFinanceDAO {
    public void saveOrUpdate(HouseFinance houseFinance);

    //某一个houseId对应的所有plan的财务情况
    public List findByHouseId(int houseId);

    //不同的场馆的财务情况
    public List find();

    List getStatistic(int houseId);
}
