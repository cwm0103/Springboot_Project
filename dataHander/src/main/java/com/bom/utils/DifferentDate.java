package com.bom.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *  方式一的话，只是通过日期来进行比较两个日期的相差天数的比较，没有精确到相差到一天的时间。如果是只是纯粹通过日期（年月日）来比较比较的话就是方式一。
    对于方式二，是通过计算两个日期相差的毫秒数来计算两个日期的天数差的。一样会有一个小问题，就是当他们相差是23个小时的时候，它就不算一天了。如下面的两个日期
 */

/**
 * Created by chenwangming on 2017/8/22.
 */
public  class DifferentDate {

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0) //闰年
                {
                    timeDistance += 366;
                }
                else //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 通过时间小时数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByhour(Date date1,Date date2)
    {
        int hour = (int) ((date2.getTime() - date1.getTime()) / (1000*3600));
        return hour;
    }

    /**
     * 日期加一年
     * @param date
     * @return
     */
    public static Date addoneyear(Date date,int year)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);// 今天+1天
        Date tomorrow = c.getTime();
        return tomorrow;
    }

    /**
     * 日期加一月
     * @param date
     * @return
     */
    public static Date addonemonth(Date date,int month)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);// 今天+1天
        Date tomorrow = c.getTime();
        return tomorrow;
    }
    /**
     * 日期加一天
     * @param date
     * @return
     */
    public static Date addonedate(Date date,int day)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);// 今天+1天
        Date tomorrow = c.getTime();
        return tomorrow;
    }

    /**
     *  日期加一小时
     * @param date
     * @param time
     * @return
     */
    public static Date addonetime(Date date,int time)
    {
        Calendar ca=Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, time);
        return ca.getTime();
    }

    /**
     * 日期加一天
     * @param date
     * @return
     */
    public static Date addonedateByMillisecond(Date date)
    {
        long time=date.getTime()+24*3600*1000;
        try   {
            Date  d  =  new Date(time);
           return d;
        }
        catch(Exception ex) {
            return new Date();
        }
    }
    //比较日期大小算出秒数
    public static  long getmillisecond(Date date1,Date date2)
    {
        long millisecond=0;
        if(date1.getTime()>date2.getTime())
        {
            millisecond= (date1.getTime()-date2.getTime())/1000;
        }else
        {
            millisecond= (date2.getTime()-date1.getTime())/1000;
        }
        return millisecond;
    }

    // 自造24小时制
    public static List<Date> time24hours(Date time) throws ParseException {
        List<Date> datetime=new ArrayList<Date>();
        //region old
//        for(int i=0;i<24;i++)
//        {
//            String hh="";
//            if(i<10)
//            {
//                hh="0"+i;
//            }else
//            {
//                hh=String.valueOf(i);
//            }
//            String pattan="yyyy-MM-dd "+hh+":00:00";
//            SimpleDateFormat sdf = new SimpleDateFormat(pattan);
//            String dtime=sdf.format(time);
//            ParsePosition pos=new ParsePosition(0);
//            Date timeone= sdf.parse(dtime,pos);
//
//            datetime.add(timeone);
//        }
        //endregion

        String pattan="yyyy-MM-dd 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(pattan);
        ParsePosition pos=new ParsePosition(0);
        Date timeone= sdf.parse(sdf.format(time),pos);

        //region new
        for(int i=0;i<24;i++)
        {
            datetime.add(addonetime(timeone,i));
        }
        //endregion
        return datetime;
    }


    //把开始日期到结束日期放在List<Date>集合中
    public static  List<Date> getListDate(Date start,Date end) throws ParseException {
        String pattan="yyyy-MM-dd 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(pattan);
        Date starttime= sdf.parse(sdf.format(start));
        int days= differentDays(start,end);
        List<Date> listDay=new ArrayList<Date>();
        //listDay.add(starttime);
        for(int i=0;i<=days;i++)
        {
            Date dateadd=addonedate(starttime,i);
            listDay.add(dateadd);
        }
        return listDay;
    }
    //获取数组最小数据的索引
    public static int getMin(long[] arr)
    {
        long mix = arr[0];
        int index=0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] < mix){
                mix = arr[i];
            }
        }
        for(int j=0;j<arr.length;j++)
        {
            if(arr[j]==mix)
            {
                index=j;
                break;
            }
        }
        //int index= Arrays.binarySearch(arr,mix); 有问题
        return index;
    }

    //一个日期集合和一个日期对比最
    public static Date getmixcompore(List<Date> datelist,Date date)
    {
            long[] arr=new long[datelist.size()];

            for(int i=0;i<datelist.size();i++)
            {
               long length= getmillisecond(datelist.get(i),date);
               arr[i]=length;
            }
            int index= getMin(arr);

           return datelist.get(index);
    }
    //开始时间和结束时间
    public static  List<String> DataList(String begin,String end)
    {
        List<String> ListData=new LinkedList<String>();
        try {
            String pattan="yyyy-MM-dd HH:mm:ss";
            String pattan2="yyyy-MM-dd HH";
            SimpleDateFormat sdf = new SimpleDateFormat(pattan);
            SimpleDateFormat sdfformat = new SimpleDateFormat(pattan2);
            ParsePosition pos=new ParsePosition(0);
//            Date timebegin= sdf.parse(sdf.format(begin),pos);
//            Date timeend= sdf.parse(sdf.format(end),pos);
            Date timebegin= sdf.parse(begin);
            Date timeend= sdf.parse(end);
            int hourData=differentDaysByhour(timebegin,timeend);
            for(int i=0;i<=hourData;i++)
            {
                ListData.add(sdfformat.format(addonetime(timebegin,i)));
            }
            return ListData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListData;
    }



    //获取前月的第一天
    public static String qianYueOneDay()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar   cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return format.format(cal_1.getTime());
    }
    //获取前月的最后一天
    public static String qianYueLastDay()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
        return format.format(cale.getTime());
    }
    //获取当前月第一天
    public static String danYueOneDay()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return format.format(c.getTime());
    }
    //获取当前月最后一天
    public static String danYueLastDay()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        //获取当前月最后一天
         Calendar ca = Calendar.getInstance();
         ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
         return  format.format(ca.getTime());
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 格式化日期
     * @param date 日期对象
     * @return String 日期字符串
     */
    /**
     * 默认日期格式
     */
    public static String DEFAULT_FORMAT = "yyyy-MM-dd 00:00:00";
    public static String formatDate(Date date){
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 获取当年的第一天
     * @param year
     * @return
     */
    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     * @param year
     * @return
     */
    public static Date getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }
}
