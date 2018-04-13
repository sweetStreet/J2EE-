package cn.yuki.dao;

import cn.yuki.entity.House;

import java.util.List;

public interface
HouseDAO {
    public int save(House house);
    public House find(String id);
    public List findByState(String state);
    public List findAll();
    public void update(House house);
}
