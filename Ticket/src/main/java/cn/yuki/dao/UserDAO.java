package cn.yuki.dao;

import cn.yuki.entity.User;

import java.util.List;

public interface UserDAO {
    public int save(User u);
    public User find(String email);
    public User find(int userId);
    public void update(User u);
}
