package com.bom.dataservice.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenwangming on 2017/11/11.
 * 生成随机数类
 */
public  class SimulationHelper {

    /**
     * 指定大小生成随机数
     * @param min
     * @param max
     * @return
     */
    public static int CreateRandom(int min,int max)
    {
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }

    /**
     * 指定大小生成随机数
     * @param min
     * @param max
     * @return
     */
    public static Double CreateRandomDouble(int min,int max)
    {
        DecimalFormat df   = new DecimalFormat("######0.00");
        double d = min + Math.random() * max % (max - min + 1);
        return new Double(String.valueOf(df.format(d)));
    }


    /**
     * 生成范围里的随机数
     * @param prev 前一个值
     * @param min 最小值
     * @param max 最大值
     * @param interval 间隔
     * @return
     */
    public static double Create(int prev, int min, int max, int interval)
    {
        int a = (int)prev - interval;
        int b = (int)prev + interval;
        if (a < min)
        {
            a = min;
        }
        if (b > max)
        {
            b = max;
        }
        return CreateRandomDouble(a, b);
    }

    /**
     *  生成一组随机数,相连两点的间隔有范围
     * @param num 个数
     * @param min 最小值
     * @param max 最大值
     * @param interval 相连两点最大误差范围的绝对值
     * @return
     */
    public static List<Double> CreateList(int num, int min, int max, int interval)
    {
        List<Double> ls = new LinkedList<Double>();
        double temp = 0;
        for (int i = 0; i < num; i++)
        {
            if (temp == 0)
            {
                temp = CreateRandomDouble(min, max);
            }
            else
            {
                int a = (int)temp - interval;
                int b = (int)temp + interval;
                if (a < min)
                {
                    a = min;
                }
                if (b > max)
                {
                    b = max;
                }
                temp = CreateRandomDouble(a, b);
            }
            ls.add(temp);
        }
        return ls;
    }


    /**
     * 生成一组随机数,相连两点的间隔有范围
     * @param num 个数
     * @param min 最小值
     * @param max 最大值
     * @param interval 相连两点最大误差范围的绝对值
     * @return
     */
    public static List<Integer> CreateIntList(int num, int min, int max, int interval)
    {
    List<Integer> ls = new LinkedList<Integer>();
    int temp = 0;
    for (int i = 0; i < num; i++)
    {
        if (temp == 0)
        {
            temp = CreateRandom(min, max);
        }
        else
        {
            int a = (int)temp - interval;
            int b = (int)temp + interval;
            if (a < min)
            {
                a = min;
            }
            if (b > max)
            {
                b = max;
            }
            temp = CreateRandom(a, b);
        }
        ls.add(temp);
    }
    return ls;
}

    /*
         * 将时间戳转换为时间
         */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
}
//region 随机数生成
//        double随机数
//
//        要获取一个[x,y)的double类型的随机数 | 左闭右开
//
//        double d = x + Math.random() * (y - x);
//
//        要获取一个(x,y]的double类型的随机数 | 左开右闭
//
//        double d = y - Math.random() * (y - x);
//
//        要获取一个[x,y]的double类型的随机数 | 左闭右闭
//
//        double d = x + Math.random() * y % (y - x + 1);
//
//        要获取一个(x,y)的double类型的随机数 | 左开右开
//
//        double d = (y - Math.random()) % y;
//
//        int随机数
//
//        要获取一个[x,y)的int类型的随机数 | 左闭右开
//
//        int d = x + (int)(Math.random() * (y - x));
//
//        要获取一个(x,y]的int类型的随机数 | 左开右闭
//
//        int d = y - (int)(Math.random() * (y - x));
//
//        要获取一个[x,y]的int类型的随机数 | 左闭右闭
//
//        int i = x + (int)(Math.random() * (y - x + 1));
//
//        要获取一个(x,y)的int类型的随机数 | 左开右开
//
//        int d = (int)((y - Math.random()) % y);

//    注意2
//
//    Math类中有一floor(double a)方法
//    public static double floor(double a)
//返回最大的（最接近正无穷大）double 值，该值小于等于参数，并等于某个整数。
//
//        所以对于要获取一个[x,y]的double类型的随机数 | 左闭右闭时候
//        可以采用以下解决思路：
//        Double.valueOf( x + Math.floor(random.nextDouble() * y % (y - x + 1));
//endregion