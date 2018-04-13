package cn.yuki.dao;

import cn.yuki.entity.Purse;

public interface PurseDAO {
    public Purse findByUserId(int userId);

    public void update(Purse purse);
}
