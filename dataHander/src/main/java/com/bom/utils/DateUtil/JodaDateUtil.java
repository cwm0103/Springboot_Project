package com.bom.utils.DateUtil;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jindb on 2017/8/2.
 *
 * @author: jindb
 * @date: 2017/8/2 9:18
 * @description:
 */
public class JodaDateUtil {
    /**
     * 格式化时间格式  yyyy-MM-dd HH:mm:ss EE
     * @param datetime
     * @return
     */
    public static  String DateFormat(DateTime datetime) {
       return  datetime.toString("yyyy-MM-dd HH:mm:ss EE");
    }

    /**
     * 格式化时间格式 yyyy-MM-dd
     * @param datetime
     * @return
     */
    public static  String DateFormatYYYYMMDD(DateTime datetime) {
        return  datetime.toString("yyyy-MM-dd");
    }

    /**
     * 格式化时间格式 yyyy-MM-dd HH:mm:ss
     * @param datetime
     * @return
     */
    public static  String DateFormatYYYYMMDDHHmmss(DateTime datetime) {
        return  datetime.toString("yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }
    /**
     * 日期格式字符串转换成时间戳
     * @param date_str 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String timeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }

    /*
     *传入日期返回对应周一和周日
     */
    public Map<String,Object> getFistDayAndLastDay(String dayStr) throws Exception{
        Map<String,Object> result=new HashMap();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time=sdf.parse(dayStr);
        cal.setTime(time);
        System.out.println("要计算日期为:"+sdf.format(cal.getTime())); //输出要计算日期
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if(1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        System.out.println("所在周星期一的日期："+sdf.format(cal.getTime()));
        result.put("monday",sdf.format(cal.getTime()));
        System.out.println(cal.getFirstDayOfWeek()+"-"+day+"+6="+(cal.getFirstDayOfWeek()-day+6));
        cal.add(Calendar.DATE, 6);
        System.out.println("所在周星期日的日期："+sdf.format(cal.getTime()));
        result.put("sunday",sdf.format(cal.getTime()));
        return result;
    }
}
