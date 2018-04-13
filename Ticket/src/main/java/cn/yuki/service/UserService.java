package cn.yuki.service;

import cn.yuki.entity.Purse;
import cn.yuki.entity.User;
import cn.yuki.service.util.Result;

import java.util.List;

public interface UserService {
    public Result login(String email, String password);

    public Result processRegister(String email, String password);

    public Result processActivate(String email, String validateCode);

    public User getUserInfo(String email);

    public Result reviseProfile(String email, String rePassword, String username);

    public Result deleteAccount(String email);

    public Result checkPay(int userId, String password, double total);

    public Purse getPurseInfo(int userId);

    public List getUserFinanceDetail(int userId, int state);

    public double getUserFinance(int userId, int state);
}
