package com.qishenghe.developassistant.chronology;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 时间处理工具
 *
 * @author qishenghe
 * @date 2020/12/29 19:09
 * @change 2020/12/29 19:09 by qishenghe for init
 */
public class DateTimeUtil {

    /**
     * 日期时间转毫秒时间戳
     *
     * @param dateTime 日期时间
     * @param pattern  格式化
     * @return 毫秒时间戳
     * @author qishenghe
     * @date 2021/9/29 14:32
     * @change 2021/9/29 14:32 by qishenghe for init
     * @since 1.0.0
     */
    public static long dateTimeToTimeStamp(String dateTime, String pattern) {

        Calendar calendar = Calendar.getInstance();
        long timeStamp;
        try {
            calendar.setTime(new SimpleDateFormat(pattern).parse(dateTime));
            timeStamp = calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("时间处理工具：异常：原因：" + e.getMessage());
        }
        return timeStamp;
    }

    /**
     * 毫秒时间戳转日期时间
     *
     * @param timeStamp 毫秒时间戳
     * @param pattern   格式化
     * @return 日期时间
     * @author qishenghe
     * @date 2021/9/29 14:32
     * @change 2021/9/29 14:32 by qishenghe for init
     * @since 1.0.0
     */
    public static String timeStampToDateTime(long timeStamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(timeStamp);
    }

}
