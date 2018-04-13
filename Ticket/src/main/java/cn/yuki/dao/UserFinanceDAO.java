package cn.yuki.dao;

import cn.yuki.entity.UserFinance;

import java.util.List;

public interface UserFinanceDAO {
    void saveOrUpdate(UserFinance userFinance);
    List find();
    List userFind(int userId, int state);
    double userFindTotal(int userId, int state);
}
