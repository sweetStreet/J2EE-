package cn.yuki.service;

import cn.yuki.entity.House;
import cn.yuki.service.util.Result;

import java.util.List;

public interface HouseService {
    public int register(String name, String location,String rows, String lines, String email);

    public Result login(String account, String password);

    public List getAllHouse(String state);

    public void passHouse(String id, int state);

    public void rejectHouse(String id, String reason);

    public House getHouseInfo(String account);

    public House getHouseInfo(int id);

    public String getStatistic(int houseId);

    Result reviseHouse(String houseId, String name, String location,String row, String line, String email);

    Result reviseHousePassword(String houseId, String password);
}
