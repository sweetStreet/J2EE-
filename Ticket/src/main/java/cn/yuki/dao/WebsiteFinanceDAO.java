package cn.yuki.dao;

import cn.yuki.entity.WebsiteFinance;

import java.util.List;

public interface WebsiteFinanceDAO {
    void saveOrUpdate(WebsiteFinance websiteFinance);
    List findGroupByPlanId();
    List findGroupByHouseId();
}
