package cn.yuki.dao;

import cn.yuki.entity.Plan;

import java.util.List;

public interface PlanDAO {

    public int save(Plan plan);

    public List findAll();

    public List findByType(String type);

    public Plan find(String id);

    public List findByHouse(int houseId);

    public List findFinishedList();

    void update(Plan plan);
}
