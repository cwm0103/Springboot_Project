package com.func.tools;

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

}
