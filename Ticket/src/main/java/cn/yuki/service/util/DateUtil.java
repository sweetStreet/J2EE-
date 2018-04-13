package cn.yuki.service.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getDateWeekBefore(Date date, int week){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,-7*week);
        return cal.getTime();
    }

    public static Date getDateDayBefore(Date date, int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,-1*day);
        return cal.getTime();
    }
}
