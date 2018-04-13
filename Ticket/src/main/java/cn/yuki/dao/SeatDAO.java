package cn.yuki.dao;

import cn.yuki.entity.Seat;

public interface SeatDAO {
    public void update(Seat seat);

    public Seat find(int planid);
}
