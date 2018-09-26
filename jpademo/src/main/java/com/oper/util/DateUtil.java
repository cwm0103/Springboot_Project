package com.oper.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: jindb
 * @Date: 2018/8/17 10:49
 * @Description:
 */
public class DateUtil {
    private  static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    private  static   SimpleDateFormat sdfMM=new SimpleDateFormat("yyyy-MM");
    private  static   SimpleDateFormat day=new SimpleDateFormat("dd");
    private  static   SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  static   SimpleDateFormat yyyyMMddHHmmss=new SimpleDateFormat("yy/M/d h:m:s");
    private  static   SimpleDateFormat sdfhms=new SimpleDateFormat("HH:mm:ss");
    private  static   SimpleDateFormat sdfhm=new SimpleDateFormat("HH:mm");
    private  static   SimpleDateFormat sdfms=new SimpleDateFormat("mm:ss");

   private static final   Map<Integer,String> mapFormat;
   private static final   Map<String,Integer> mapDatetype;

   static {
       Map aMap = new HashMap();
       aMap.put(Calendar.YEAR, "yyyy");
       aMap.put(Calendar.MONTH, "yyyy-MM");
       aMap.put(Calendar.DAY_OF_MONTH, "yyyy-MM-dd");
       aMap.put(Calendar.HOUR_OF_DAY, "yyyy-MM-dd HH:00");
       aMap.put(Calendar.MINUTE, "yyyy-MM-dd HH:mm:00");
       mapFormat= Collections.unmodifiableMap(aMap);
       Map bMap=new HashMap();
       aMap.put("YY", Calendar.YEAR);
       aMap.put("MM", Calendar.MONTH);
       aMap.put("DD", Calendar.DAY_OF_MONTH);
       aMap.put("HH", Calendar.HOUR_OF_DAY);
       aMap.put("MI", Calendar.MINUTE);
       mapDatetype=Collections.unmodifiableMap(aMap);

   }

    public static Date add(Date date, int type, int day){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type, day);
        return c.getTime();
    }

    public static List<String>  findDates(Date dBegin, Date dEnd,String datetype){
        List<String> lDate = new ArrayList<String>();
        SimpleDateFormat sd = new SimpleDateFormat(mapFormat.get(mapDatetype.get(datetype)));
        lDate.add(sd.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量;
            calBegin.add(mapDatetype.get(datetype), 1);
            lDate.add(sd.format(calBegin.getTime()));
        }
        return lDate;
    }


}

