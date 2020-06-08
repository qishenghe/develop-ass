package cn.qishenghe.developassistant.util.timeutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期时间处理工具
 */
public class DateTimeUtil {

    /**
     * 日期时间转毫秒时间
     */
    public static Long dateToStamp (String date, String pattern) {

        Calendar calendar = Calendar.getInstance();
        long time_Stamp = 0L;
        try {
            calendar.setTime(new SimpleDateFormat(pattern).parse(date));
            time_Stamp = calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time_Stamp;

    }

    /**
     * 时间戳转日期时间
     */
    public static String stampToDate (long time_Stamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(time_Stamp);
    }

}
